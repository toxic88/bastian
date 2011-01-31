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

    public static enum ReadyState {
        CONNECTING, OPEN, CLOSING, CLOSED
    }

    protected Socket() {}

    public final native void send(String message) /*-{
        this.send(message);
    }-*/;

    public final native void close() /*-{
        this.close();
    }-*/;

    public final native ReadyState getReadyState() /*-{
        switch (this.readyState) {
            case 0:
                return @com.google.gwt.appengine.channel.client.Socket.ReadyState::CONNECTING;
            case 1:
                return @com.google.gwt.appengine.channel.client.Socket.ReadyState::OPEN;
            case 2:
                return @com.google.gwt.appengine.channel.client.Socket.ReadyState::CLOSING;
            case 3:
                return @com.google.gwt.appengine.channel.client.Socket.ReadyState::CLOSED;
        }
        return null;
    }-*/;

}
