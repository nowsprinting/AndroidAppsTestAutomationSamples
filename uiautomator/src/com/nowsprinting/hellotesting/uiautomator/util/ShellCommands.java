/*
 * Copyright 2014 TOYAMA Sumio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nowsprinting.hellotesting.uiautomator.util;

import android.content.Intent;

import com.android.uiautomator.core.UiDevice;

import junit.framework.Assert;

import java.io.IOException;
import java.util.Arrays;

/**
 * 端末内のシェルコマンド発行に関係するメソッドを定義したクラス。
 */
public class ShellCommands {

    /**
     * 指定されたパッケージ名とアクティビティ名を元に、アプリを起動する。
     * 既に起動済みの場合は、一旦強制終了してから起動する。
     * 
     * @param packageName 起動したいパッケージ名
     * @param activityName 起動したいアクティビティ名
     * @throws IOException 起動中にエラーが発生した場合
     * @throws InterruptedException 起動待ち中に割り込みが発生した場合
     */
    public static void startApp(String packageName, String activityName) throws IOException,
            InterruptedException {
        String componentName = String.format("%s/%s", packageName, activityName);
    
        // コマンドラインオプションの説明:
        // -S: force stop the target app before starting the activity
        // -W: wait for launch to complete
        ShellCommands.executeWithoutIo("am", "start", "-S", "-W", "-a", Intent.ACTION_MAIN,
                "-n", componentName);
        Assert.assertEquals(packageName, UiDevice.getInstance().getCurrentPackageName());
    }

    /**
     * 指定されたコマンドを起動し、終了するまで待つ。
     * 標準入力には何も渡されず、標準出力・標準エラー出力も無視する。
     * 
     * @param command コマンド引数の配列
     * @throws IOException 起動したコマンドの終了コードが非ゼロの場合
     * @throws InterruptedException コマンド終了待ち中に割り込みが発生した場合
     */
    public static void executeWithoutIo(String... command) throws IOException,
            InterruptedException {
        Process p = new ProcessBuilder(command).start();
        int exitStatus = p.waitFor();
        if (exitStatus != 0) {
            throw new IOException(String.format("Non-zero exit status(%d): %s", exitStatus,
                    Arrays.toString(command)));
        }
    }

}
