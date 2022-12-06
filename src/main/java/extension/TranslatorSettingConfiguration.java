package extension;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.JBColor;

import org.jetbrains.annotations.Nullable;

public class TranslatorSettingConfiguration implements Configurable {

    private final JComponent component;
    private final JTextField appId;
    private final JTextField securityKey;
    private final static String appIDHint = "请输入appID";
    private final static String securityKeyHint = "请输入securityKey";
    //    private final static String appIDHint = "20160728000025950";
    //    private final static String securityKeyHint = "iLQNUxz23Oli_PHaeuuL";

    public TranslatorSettingConfiguration() {
        this.component = new JPanel();
        this.component.setSize(200, 20);
        this.component.setLayout(new GridLayout(15, 1));

        // 创建appID、securityKey文本框
        this.appId = new JTextField();
        this.securityKey = new JTextField();

        this.appId.addFocusListener(new TextFieldListener(this.appId, appIDHint));
        if (TranslatorSetting.getInstance().appId != null) {
            this.appId.setText(TranslatorSetting.getInstance().appId);
        } else {
            //设置输入框提示语
            this.appId.setText(appIDHint);
            this.appId.setForeground(JBColor.GRAY);
        }

        this.securityKey.addFocusListener(new TextFieldListener(this.securityKey, securityKeyHint));
        if (TranslatorSetting.getInstance().securityKey != null) {
            this.appId.setText(TranslatorSetting.getInstance().securityKey);
        } else {
            //设置输入框提示语
            this.securityKey.setText(securityKeyHint);
            this.securityKey.setForeground(JBColor.GRAY);
        }

        this.component.add(this.appId);
        this.component.add(this.securityKey);
    }

    @Override
    public String getDisplayName() {
        return "Translator";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return component;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        //        TranslatorUtils.appid = appId.getText();
        //        TranslatorUtils.securityKey = securityKey.getText();
        TranslatorSetting.getInstance().appId = appId.getText();
        TranslatorSetting.getInstance().securityKey = securityKey.getText();
    }

    static class TextFieldListener implements FocusListener {

        private final String defaultHint;
        private final JTextField textField;

        public TextFieldListener(JTextField textField, String defaultHint) {
            this.defaultHint = defaultHint;
            this.textField = textField;
        }

        @Override
        public void focusGained(FocusEvent e) {
            // 清空提示语，设置为黑色字体
            if (textField.getText().equals(defaultHint)) {
                textField.setText("");
                textField.setForeground(JBColor.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            // 如果内容为空，设置提示语
            if (textField.getText().equals("")) {
                textField.setText(defaultHint);
                textField.setForeground(JBColor.GRAY);
            }
        }
    }
}