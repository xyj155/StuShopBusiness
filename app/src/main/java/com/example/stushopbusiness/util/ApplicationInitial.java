package com.example.stushopbusiness.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.stushopbusiness.MyApp;
import com.example.stushopbusiness.R;
import com.example.stushopbusiness.weight.toast.ToastUtils;
import com.mob.MobSDK;
import com.payelves.sdk.EPay;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.ui.UILifecycleListener;
import com.tencent.smtt.sdk.QbSdk;

import java.io.IOException;
import java.text.SimpleDateFormat;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.push.RongPushClient;

import static io.rong.eventbus.EventBus.TAG;
import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

public class ApplicationInitial {
    public ApplicationInitial initArouter() {
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(MyApp.getInstance());
        return this;
    }

    public ApplicationInitial initEpay() {
        EPay.getInstance(MyApp.getInstance()).init("wAwS4BHkB", "1b0ccf51458c4053ae2931772fbbfb97",
                "7169149861036033", "baidu");
        return this;
    }

    public ApplicationInitial initRongIm() {
        RongIM.init(MyApp.getInstance(), "0vnjpoad033gz");
        connect((String) SharePreferenceUtil.getUser("imToken", "String"));
        return this;
    }


    private void connect(String token) {

        if (MyApp.getInstance().getApplicationInfo().packageName.equals(getCurProcessName(MyApp.getInstance()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.i(TAG, "onTokenIncorrect: ");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("LoginActivity", "--onSuccess" + userid);
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
                    Log.i(TAG, "onConnectSuccess: ");
//                    ConversationListFragment listFragment = (ConversationListFragment) ConversationListFragment.instantiate(getContext(), ConversationListFragment.class.getName());
//                    Uri uri = Uri.parse("rong://" + MyApp.getInstance().getApplicationInfo().packageName).buildUpon()
//                            .appendPath("conversationlist")
//                            .appendQueryParameter(RongPushClient.ConversationType.PRIVATE.getName(), "false")
//                            .appendQueryParameter(RongPushClient.ConversationType.GROUP.getName(), "false")
//                            .appendQueryParameter(RongPushClient.ConversationType.DISCUSSION.getName(), "false")
//                            .appendQueryParameter(RongPushClient.ConversationType.PUBLIC_SERVICE.getName(), "false")
//                            .appendQueryParameter(RongPushClient.ConversationType.SYSTEM.getName(), "false")
//                            .build();
//                    listFragment.setUri(uri);
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    //将融云的Fragment界面加入到我们的页面。
//                    transaction.add(R.id.conversationlist, listFragment);
//                    transaction.commitAllowingStateLoss();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.i(TAG, "onError: "+errorCode);
                }
            });
        }
    }
    public ApplicationInitial initMob() {
//        MobPush.addTags(java.lang.String[] tags);
        MobSDK.init(MyApp.getInstance(), "29cbff9d24b0b", "83fe8985b2647f0041f9cfb3487492d6");
        return this;
    }

    public ApplicationInitial initJpush() {
        CustomPushNotificationBuilder builder = new
                CustomPushNotificationBuilder(MyApp.getInstance(),
                R.layout.customer_notitfication_layout,
                R.id.icon,
                R.id.title,
                R.id.text);
        // 指定定制的 Notification Layout
        builder.statusBarDrawable = R.mipmap.app_icon;
        // 指定最顶层状态栏小图标
        builder.layoutIconDrawable = R.mipmap.app_icon;
        // 指定下拉状态栏时显示的通知图标
        JPushInterface.setPushNotificationBuilder(2, builder);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(MyApp.getInstance());

        return this;
    }

    public ApplicationInitial initBuygly() {
        Beta.autoCheckUpgrade = true;
        Beta.largeIconId = R.mipmap.app_icon;
        Beta.smallIconId = R.mipmap.app_icon;
        Beta.initDelay = 20 * 1000;
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;
        Beta.smallIconId = R.mipmap.app_icon;
        Beta.strUpgradeDialogCancelBtn = MyApp.getInstance().getString(R.string.text_remind_late);
        Beta.strNetworkTipsConfirmBtn = MyApp.getInstance().getString(R.string.update_now);
        Beta.strUpgradeDialogUpgradeBtn = MyApp.getInstance().getString(R.string.update_now);
        Beta.strUpgradeDialogInstallBtn = MyApp.getInstance().getString(R.string.install_now);
        Beta.upgradeDialogLifecycleListener = new UILifecycleListener<UpgradeInfo>() {
            @Override
            public void onCreate(Context context, View view, UpgradeInfo upgradeInfo) {


            }

            @Override
            public void onStart(Context context, View view, UpgradeInfo upgradeInfo) {

            }

            @Override
            public void onResume(Context context, View view, UpgradeInfo upgradeInfo) {

                TextView textView = (TextView) view.findViewWithTag("beta_upgrade_feature");
                StringBuilder info = new StringBuilder();
                info.append("更新日志: ").append(upgradeInfo.newFeature).append("\n\n");
                info.append("版本名称: ").append(upgradeInfo.versionName).append("\n\n");
                info.append("发布时间: ").append(getCurDate("yyyy-mm-dd", upgradeInfo.publishTime)).append("\n\n");
                info.append("安装包大小: ").append(SizeConverter.BTrim.convert(upgradeInfo.fileSize)).append("\n");
                textView.setText(info);

            }

            @Override
            public void onPause(Context context, View view, UpgradeInfo upgradeInfo) {

            }

            @Override
            public void onStop(Context context, View view, UpgradeInfo upgradeInfo) {

            }

            @Override
            public void onDestroy(Context context, View view, UpgradeInfo upgradeInfo) {

            }

        };
        Bugly.init(MyApp.getInstance(), "b705191993", false);

        return this;
    }

    public static String getCurDate(String pattern, long time) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(time);
    }




    /**
     * 配置 APP 保存图片/语音/文件/log等数据的目录
     * 这里示例用SD卡的应用扩展存储目录
     */
    static String getAppCacheDir(Context context) {
        String storageRootPath = null;
        try {
            // SD卡应用扩展存储区(APP卸载后，该目录下被清除，用户也可以在设置界面中手动清除)，请根据APP对数据缓存的重要性及生命周期来决定是否采用此缓存目录.
            // 该存储区在API 19以上不需要写权限，即可配置 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" android:maxSdkVersion="18"/>
            if (context.getExternalCacheDir() != null) {
                storageRootPath = context.getExternalCacheDir().getCanonicalPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(storageRootPath)) {
            // SD卡应用公共存储区(APP卸载后，该目录不会被清除，下载安装APP后，缓存数据依然可以被加载。SDK默认使用此目录)，该存储区域需要写权限!
            storageRootPath = Environment.getExternalStorageDirectory() + "/" + MyApp.getInstance().getPackageName();
        }

        return storageRootPath;
    }


    public ApplicationInitial initPermission() {

        return this;
    }
    public ApplicationInitial initToast() {
        ToastUtils.init(MyApp.getInstance());
        return this;
    }
    public ApplicationInitial initX5() {
        //x5內核初始化回调
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {


            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onViewInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.preInit(MyApp.getInstance(), cb);
        return this;
    }
}
