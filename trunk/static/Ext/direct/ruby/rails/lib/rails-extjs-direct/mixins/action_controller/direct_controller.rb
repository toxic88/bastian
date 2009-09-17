module Rails::ExtJS::Direct::Controller

    # standard ruby method called when some class does:
    # include Merb::ExtJS::Direct::RemotingProvider
    def self.included(base)
        base.before_filter :extjs_direct_prepare_request
    end

    def extjs_direct_prepare_request
        @xrequest = XRequest.new(params)
    end
end
