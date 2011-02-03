/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.gwt.appengine.channel.client;

import com.google.gwt.core.client.JavaScriptObject;

/** Returned by calls to {@link Channel#open(SocketListener)}. */
public class Socket extends JavaScriptObject {

    public static final int CONNECTING = 0;
    public static final int OPEN = 1;
    public static final int CLOSING = 2;
    public static final int CLOSED = 3;

    protected Socket() {}

    public final native void send(String message) /*-{
        this.send(message);
    }-*/;

    public final native void close() /*-{
        this.close();
    }-*/;

    public final native int getReadyState() /*-{
        return this.readyState;
    }-*/;

}
