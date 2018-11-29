/*
 * Copyright (c) 2017. tangzx(love.tangzx@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tang.intellij.lua.debugger.unity

import com.intellij.execution.process.ProcessInfo
import com.intellij.xdebugger.XDebugSession
import com.tang.intellij.lua.debugger.attach.LuaAttachBridge
import com.tang.intellij.lua.debugger.attach.LuaAttachBridgeBase
import com.tang.intellij.lua.debugger.attach.LuaAttachDebugProcessBase
import com.tang.intellij.lua.debugger.utils.ProcessUtils

/**
 *
 * Created by Taigacon on 2018/11/6.
 */
class LuaUnityDebugProcess internal constructor(session: XDebugSession, private val configuration: LuaUnityConfiguration)
    : LuaAttachDebugProcessBase(session) {

    private fun queryUnityProcess(): ProcessInfo? {
        val list = UnityProcessDiscovery.getAttachableProcesses(GetProcessOptions.All)
        if (list.isNotEmpty()) {
            val processMap = ProcessUtils.listProcesses()
            for (processInfo in list) {
                processMap[processInfo.pid]?.run {
                    if (title.contains(configuration.preferredUnityInstanceName, false)) {
                        return processInfo
                    }
                }
            }
        }
        return null
    }

    override fun startBridge(): LuaAttachBridgeBase {
        val bridge = LuaAttachBridge(this, session)
        this.bridge = bridge
        bridge.setProtoHandler(this)
        val processInfo = queryUnityProcess()
        if (processInfo == null) {
            if (configuration.preferredUnityInstanceName.isNotEmpty())
                error("Cannot find suitable Unity instance for prefered instance name: ${configuration.preferredUnityInstanceName}")
            else
                error("Cannot find suitable Unity instance")
            session.stop()
        } else {
            bridge.attach(processInfo)
        }
        return bridge
    }
}