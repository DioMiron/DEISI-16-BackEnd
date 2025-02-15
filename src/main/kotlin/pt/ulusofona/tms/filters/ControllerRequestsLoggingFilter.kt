/*-
 * ========================LICENSE_START=================================
 * DropProject
 * %%
 * Copyright (C) 2019 Pedro Alves
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */
package pt.ulusofona.tms.filters

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.filter.AbstractRequestLoggingFilter

class ControllerRequestsLoggingFilter : AbstractRequestLoggingFilter() {

    init {
        isIncludeClientInfo = true
        setAfterMessagePrefix("[REQ] ")
    }

    override fun shouldLog(request: HttpServletRequest): Boolean {
        return logger.isInfoEnabled &&
                !request.requestURI.orEmpty().endsWith(".css") &&
                !request.requestURI.orEmpty().endsWith(".js") &&
                !request.requestURI.orEmpty().endsWith(".ico") &&
                !request.requestURI.orEmpty().endsWith(".png");
    }

    override fun beforeRequest(request: HttpServletRequest, message: String) {
        // do nothing
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {
        logger.info(message)
    }
}
