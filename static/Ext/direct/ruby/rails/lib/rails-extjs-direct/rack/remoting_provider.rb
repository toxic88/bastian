
require 'json'
class Rails::ExtJS::Direct::RemotingProvider
    def initialize(app, rpath)
        @app = app
        @router_path = rpath
    end

    def call(env)
        if env["PATH_INFO"].match("^"+@router_path)
            output = []
            parse(env["action_controller.request.request_parameters"]).each do |req|
                # have to dup the env for each request
                request_env = env.dup

                # pop poorly-named Ext.Direct params off.
                controller = req.delete("action").downcase
                action =  req.delete("method")

                # set env URI and PATH_INFO for each request
                request_env["PATH_INFO"] = "/#{controller}/#{action}"
                request_env["REQUEST_URI"] = "/#{controller}/#{action}"

                # set request params
                request_env["action_controller.request.request_parameters"] = req

                # call the app!
                status, headers, response = @app.call(request_env)
                output << response.body
            end
            # join all responses together
            res = output.join(',')

            # [wrap in array] if multiple responses.  Each controller response will have returned json already
            # so we don't want to re-encode.
            res = "[" + res + "]" if output.length > 1

            # return response
            [200, {"Content-Type" => "text/html"}, res]
        else
            @app.call(env)
        end
    end

    def parse(data)
        return (data["_json"]) ? data["_json"] : [data]
    end

end

