package iqq.app.ui.frame;


import iqq.api.bean.IMBuddyCategory;
import iqq.api.bean.IMUser;
import iqq.app.core.service.SkinService;
import iqq.app.ui.IMFrame;
import iqq.app.ui.event.UIEvent;
import iqq.app.ui.event.UIEventHandler;
import iqq.app.ui.event.UIEventType;
import iqq.app.ui.frame.panel.main.MainPane;

import java.awt.*;
import java.util.List;


/**
 * 主界面，分为上/中/下的内容面板
 *
 * Project  : iqq-projects
 * Author   : 承∮诺 < 6208317@qq.com >
 * Created  : 14-5-4
 * License  : Apache License 2.0
 */
public class MainFrame extends IMFrame {
    private MainPane contentPane;

    public MainFrame() {
        this(null);
    }

    public MainFrame(MainPane contentPane) {

        this.contentPane = contentPane;
        // 主面板，放所有显示内容
        if(contentPane == null) this.contentPane = new MainPane(this);

        initUI();
    }

    private void initUI() {
        setTitle(getI18nService().getMessage("app.name"));
        setIMContentPane(contentPane);
        setDefaultCloseOperation(IMFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);                      // 居中
        setPreferredSize(new Dimension(300, 650));        // 首选大小
        pack();
    }

    /**
     * 安装皮肤
     *
     * @param skinService
     */
    @Override
    public void installSkin(SkinService skinService) {
        super.installSkin(skinService);
        this.contentPane.installSkin(skinService);
        setIconImage(skinService.getIconByKey("window/titleWIcon").getImage());
    }

    @UIEventHandler(UIEventType.SELF_FACE_UPDATE)
    public void processSelfFaceUpdate(UIEvent uiEvent){
        contentPane.getHeaderPanel().updateSelfFace((Image) uiEvent.getTarget());
    }

    @UIEventHandler(UIEventType.SELF_INFO_UPDATE)
    public void processSelfInfoUpdate(UIEvent uiEvent){
        IMUser user = (IMUser) uiEvent.getTarget();
        contentPane.getHeaderPanel().updateSelfNick(user.getNick());
    }

    @UIEventHandler(UIEventType.SELF_SIGN_UPDATE)
    public void processSelfSignUpdate(UIEvent uiEvent){
        IMUser user = (IMUser) uiEvent.getTarget();
        contentPane.getHeaderPanel().updateSelfSign(user.getSign());
    }

    @UIEventHandler(UIEventType.BUDDY_LIST_UPDATE)
    public void processBuddyUpdate(UIEvent uiEvent){
        List<IMBuddyCategory> imCategories = (List<IMBuddyCategory>) uiEvent.getTarget();
        contentPane.getMiddlePanel().updateBuddyList(imCategories);
    }

    @UIEventHandler(UIEventType.USER_FACE_UPDATE)
    public void processUserFaceUpdate(UIEvent uiEvent){
        IMUser imUser = (IMUser) uiEvent.getTarget();
        contentPane.getMiddlePanel().updateUserFace(imUser);
    }

}
