##
# Meb::ExtJS::Direct::RemotingProvider
# @mixin
# This is a mixin which provides support Merb support for ExtJS's Ext.Direct mechanism in Ext3.x
# <h3>Defining and registering the Ext.Direct api</h3>
# <code>
# <script>
#   var api : {
#       url: 'my_controller/rpc',
#       type: 'remoting',
#       actions: []
#   };
#
#   Ext.onReady(function() {
#       // Add some actions from ArtistPart
#       api.actions.push({
#           ArtistPart : [
#               {"name" : "create"},
#               {"name" : "destroy"}
#           ];
#       });
#       .
#       .
#       .
#       // add some more actions from AlbumPart
#       api.actions.push({
#           AlbumPart : [
#               {"name" : "create"},
#               {"name" : "destroy"}
#           ];
#       });
#
#       // register api with Ext.Direct
#       Ext.Direct.addProvider(api);
#
#       // Executing Ext.Direct actions.  (very much like using irb console)
#       ArtistPart.destroy(1);
#       AlbumCreate.create({name : "Joe Puke and the Chunky Bits"});
#
#   });
# </script>
# </code>
#
# <h3>Configuring your Merb Ext.Direct controller</h3>
# <code>
# class MyController
#     include Merb::Ext::Direct::RemotingProvider   # <-- include this mixin.
# end
# </code>
#
# <p>By including RemotingProvider mixin, your controller will have added to it a single action called "rpc"
# with which you can send all you Ext.Direct actions to.  Take note of the url specified in the first step.</p>
#
# <h3>Merb-parts</h3>
# Ext.Direct is implemented in Merb by taking advantage of "Parts".  The nice thing about parts is that they
# have both a view (for rendering you Ext controls and api along with a controller for executing your api actions.
#
#
#
# The Merb RemotingProvider mixin takes advantage of the merb-parts gem.
#
# Ext.Direct router
# @usage include Ext::Direct::RemotingProvider
# @author Chris Scott
# @TODO before_actions
#
module Merb::ExtJS::Direct
    module RemotingProvider

        # standard ruby method called when some class does:
        # include Merb::ExtJS::Direct::RemotingProvider
        def self.included(base)
            # must explicity specify controller-actions to make callable.  security-wise, this is a *good* thing.
            # just one action is added.
            base.show_action(:rpc)
        end

        ##
        # rpc
        # remote procedure call handler for Ext.direct requests.
        #
        def rpc
            if !request.ajax? && !params["extAction"]
                return "Ext::Direct::RemotingProvider#rpc -- This method is an ajax-handler only"
            end

            is_form = false
            is_upload = false
            if params["extAction"] && params["extMethod"]
                # create fake response here.
                is_form = true
                is_upload = params.delete("extUpload") == 'true'

                request = {
                    "xcontroller"   => params.delete("extAction"),
                    "xaction"       => params.delete("extMethod"),
                    "type"          => "rpc",
                    "id"            => params.delete("id"),
                    "tid"           => params.delete("extTID"),
                    "format"        => params.delete("format"),
                    "data"          => params
                }
                params.delete('controller')
                params.delete('action')
                res = "<html><body><textarea>#{handle_request(request).gsub(/&quot;/, "\&quot;")}</textarea></body></html>"
                return res
            elsif (params[:inflated_object])
                # multiple requests found.  I'm not sure how this "inflated_object" mechanism works.  This is Merb magic?
                # controllers always return a string, so each response has already been json-encoded.
                # since we're dealing with multiple requests here, we have to manually string-concat-wrap the retured
                # string-of-json.
                #
                return "[" + params[:inflated_object].collect {|req| handle_request(req)}.join(',') + "]"
            else
                ##
                # just a single request found
                #
                return handle_request(params)
            end
        end

        ##
        #
        protected
        #
        ##

        ##
        # handle_request
        # # @throws XException when anything goes wrong to gracefully handle on client.
        # @see Merb::Controller#part method, google: "merb parts".  A Merb "part" is a kind of sub-controller
        # with its onwn view capabilites but cannot be directly routed-to.  Using part() method here is similar
        # to using Ext.getCmp(id), eg: part(req.controller => req.action) ~ Ext.getCmp(controller).doSomething(action);
        # However, unlike Ext.getCmp(), part(controller => action) returns a string to be added to the render stream
        # and not a component instance, just as all merb render actions.
        #
        def handle_request(req)
            req = XRequest.new(req)

            begin
                # here's the Ext.direct.Transaction as a ruby Hash.
                # turn it into an offical ruby XRequest instance.
                # constantize the part-controller and send "action" to it.
                # NOTE:  Extlib has nothing to do with ExtJS -- just a coincidence.  Extlib is a Merb gem.
                return part(Extlib::Inflection.constantize(req.controller) => req.action, :xrequest => req)
            rescue XException => e
                # caught an XException...return an Ext.Direct-friendly XExceptionResponse
                return XExceptionResponse.new(req, e).to_json
            rescue StandardError => e
                # might be an invalid controller and/or action if we got here.
                # we can be more specific here and trap on NameError if we wish, intead of base StandardError
                return XExceptionResponse.new(req, e).to_json
            end
        end
    end
end
