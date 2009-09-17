= merb-extjs-direct

http://rubyforge.org/projects/merb-extjs/
http://www.extjs.com

== DESCRIPTION:

A series of components for implementing the Ext.Direct API specification in Merb.  Ext.Direct in Merb utilizes
merb-parts.  Each part can define a CRUD api which router will dispatch requests to.

Includes a Controller mixin for giving a controller the ability to act as an Ext.Direct RemotingProvider.  This mixin
turns the Controller into an Ext.Direct router.

== FEATURES/PROBLEMS:

== SYNOPSIS:

First include the RemotingProvider mixin into a high-level controller non-specific to any particular model.  This
mixin will turn the controller into a Router, routing requests into a Merb::PartController.


This mixin will add a callable action named "rpc" to the whichever included into.  You can then set this as the
url for your Ext.Direct configuration in javascript.  For the Admin controller above, the url would be
"/admin/rpc"

class Admin < Application
  include Merb::ExtJS::Direct::RemotingProvider
end

<script>
Ext.Direct.addProvider({
    url: '/admin/rpc',  //<-- Note the url corresponding to Admin above.
    type: 'remoting',
    actions: {
        UserPart : [    // <-- UserPart is a Merb::PartController
            {name: 'load', len: 1},
            {name: 'save', len: 2},
            {name: 'create', len: 1},
            {name: 'destroy', len: 1}
        ]
    }
});
</script>

When an Ext.Direct request is serviced by the RemotingProvider mixin, it will create an XRequest instance and attach
it to the Controller's params upon the symbol :xrequest.

Following is a typical PartController receiving Ext.Direct requests.  Notice each request begins by retrieving
the XRequest instance from params then instantiating an XResponse feeding it the XRequest instance into the
constructor.

Each request ends by simply calling the to_json method upon the XResponse instance.

class UserPart < Merb::PartController
  def create
    req = params[:xrequest]
    res = XResponse.new(req)
    res.success = User.create(req.params)
    res.message = "Created user"
    res.to_json
  end

  def load
    req = params[:xrequest]
    res = XResponse.new(req)
    res.data = User.all.collect{|u|u.to_hash}
    res.success = true
    res.message = "Loaded stuff"
  end

  def save
    req = params[:xrequest]
    res = XResponse.new(req)
    if u = User.first(:id => req.id)
      res.success = u.update_attributes(req.params)
      res.message = "Saved User"
    else
      raise XException.new("Failed to load that user")
    end
    res.to_json
  end

  def destroy
    req = params[:xrequest]
    res = XResponse.new(req)
    if u = User.first(:id => req.id)
      res.success = u.destroy
      res.message = "Destroyed User"
    else
      raise XException.new("Failed to load that User"
    end
    res.to_json
  end

end

== REQUIREMENTS:

merb-parts

== INSTALL:

sudo gem install merb-extjs-parts

IMPORTANT:  This gem includes a required javascript file named "merb-extjs-direct.js" in the root of this gem.
Currently you MUST copy this file to your /public/javascripts directory and included it immediately the ext
framework's assets (ie: immediately after "ext-all.js").

merb-extjs-direct.js contains a couple of simple overrides of the Ext.Direct library which will rename two
HTTP request parameters to be more Merb/Rails-like.  By default, Ext.Direct uses the word "action" where Merb
uses "controller" and "method" vs "action".  Confusing, eh?  The HTTP params are actually renamed xcontroller
and xaction to not interfere with Merb reserved words.  If you don't see "xcontroller" / "xaction" params
in your Ajax requests, make sure you've got merb-ext-direct.js included.

In the future, I'll have >rake install automaticlly copy this file to /public/javascripts (Would anyone like to
contribute some code to do that?)

== LICENSE:

(The MIT License)

Copyright (c) 2009 FIX

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
'Software'), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
