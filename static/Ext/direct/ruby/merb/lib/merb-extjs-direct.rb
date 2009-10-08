module Merb
    module ExtJS
        module Direct
            VERSION = '0.0.4'
        end
    end
end

require File.join(File.dirname(__FILE__), 'merb-extjs-direct', 'xrequest')
require File.join(File.dirname(__FILE__), 'merb-extjs-direct', 'xresponse')
require File.join(File.dirname(__FILE__), 'merb-extjs-direct', 'xexception')
require File.join(File.dirname(__FILE__), 'merb-extjs-direct', 'mixins', 'remoting_provider')

