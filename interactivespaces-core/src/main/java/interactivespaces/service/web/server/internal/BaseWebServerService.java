/*
 * Copyright (C) 2012 Google Inc.
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

package interactivespaces.service.web.server.internal;

import interactivespaces.service.BaseSupportedService;
import interactivespaces.service.web.server.WebServer;
import interactivespaces.service.web.server.WebServerService;
import interactivespaces.service.web.server.internal.netty.NettyWebServer;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Support for creating an instance of a {@link WebServerService}.
 *
 * @author Keith M. Hughes
 */
public abstract class BaseWebServerService extends BaseSupportedService implements WebServerService {

  /**
   * Map from server name to server.
   */
  private Map<String, NettyWebServer> servers = Maps.newHashMap();

  @Override
  public void shutdown() {
    for (NettyWebServer server : servers.values()) {
      server.shutdown();
    }

    servers.clear();
  }

  @Override
  public WebServer getWebServer(String serverName) {
    return servers.get(serverName);
  }

  @Override
  public void shutdownServer(String serverName) {
    NettyWebServer server = servers.get(serverName);
    if (server != null) {
      server.shutdown();

      servers.remove(serverName);
    }
  }
}