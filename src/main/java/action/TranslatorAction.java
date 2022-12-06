/**
 * Aistarfish.com Inc.
 * Copyright (c) 2017-2022 All Rights Reserved.
 */
package action;

import java.util.Map;
import java.util.Objects;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;

import extension.TranslatorCache;
import extension.TranslatorToolsWindow;
import org.jetbrains.annotations.NotNull;
import util.TranslatorUtils;

public class TranslatorAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (TranslatorUtils.appId == null || TranslatorUtils.securityKey == null) {
            Notifications.Bus.notify(
                    new Notification("Print", "小跳跳翻译机", "请先设置appId，securityKey", NotificationType.ERROR),
                    e.getProject());
            return;
        }
        String text = Objects.requireNonNull(editor).getSelectionModel().getSelectedText();

        //获取持久化数据对象
        Map<String, String> transCache = TranslatorCache.getInstance(Objects.requireNonNull(e.getProject())).transCache;
        String transResult;
        if (transCache.containsKey(text)) {
            transResult = transCache.get(text);
        } else {
            transResult = TranslatorUtils.getTransResult(text, "auto", "zh");
            transCache.put(text, transResult);
            TranslatorToolsWindow.addNote(text, transResult);
        }
        Notifications.Bus.notify(new Notification("Print", "小跳跳翻译机", transResult, NotificationType.INFORMATION),
                                 e.getProject());
    }
}