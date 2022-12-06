package extension;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author curry
 * Created by on 2022-12-06 下午2:12
 */
@State(name = "translator", storages = {@Storage(value = "translator.xml")})
public class TranslatorSetting implements PersistentStateComponent<TranslatorSetting> {

    public String appId;
    public String securityKey;

    @Nullable
    @Override
    public TranslatorSetting getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull TranslatorSetting state) {
        this.appId = state.appId;
        this.securityKey = state.securityKey;
    }

    public static TranslatorSetting getInstance() {
        return ApplicationManager.getApplication().getService(TranslatorSetting.class);
    }
}