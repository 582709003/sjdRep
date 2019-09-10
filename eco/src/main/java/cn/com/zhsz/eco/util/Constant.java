package cn.com.zhsz.eco.util;

/**
 * 常量类
 */
public final class Constant {

    /**
     * 短信接口地址
     */
    public static final String SMSREQUESTURL = "http://132.224.218.13:8081/jsdt_services/IUVCService/realTimeSendInfo";

    /**
     * 需要登录后访问的接口前缀
     */
    public static final String LOGIN = "/land";

    /**
     * 写权限接口前缀
     */
    public static final String EDIT = "/edit/";

    /**
     * 接口调用成功返回码
     */
    public static final String SUCCESS = "0";

    /**
     * 分页字段定义:当前页码
     */
    public static final String PAGENUM = "pageNum";

    /**
     * 分页字段定义:每页条数
     */
    public static final String PAGESIZE = "pageSize";

    /**
     * 回参:总数据条数参数名
     */
    public static final String TOTAL = "totalCount";

    /**
     * 回参:数据集合参数名
     */
    public static final String ITEMS = "items";

    /**
     * 回参:单条参数名
     */
    public static final String ITEM = "item";

    /**
     * 入参:接口入参参数名
     */
    public static final String PARAMETER = "parameter";

    /**
     * 回参:审批记录列表参数名
     */
    public static final String RECORDLIST = "recordList";

    /**
     * ridis KEY:正在进行中的任务id列表 key //d9b3262aea8c7f21c94d777d0b783e02
     */
    public static final String STARTTASK = "startTask";

    /**
     * ridis KEY:正在进行中的定时器列表 key //5d68ef16ee5d5ce2cb1425d82800fa5c
     */
    public static final String JOBKEY = "jobKey";

    /**
     * 统一分隔符
     */
    public static final String SEPARATOR = "\\|s\\|";

    /**
     * 权限编号 0:编辑权限
     */
    public static final String[] ROLERIGHT = {"edit", "edit1", "edit2", "edit3", "edit4", "edit5"};

    /**
     * 权限编号 0:编辑权限
     */
    public static final String[] ROLERIGHTNAME = {"编辑权限", "编辑权限1", "编辑权限2", "编辑权限3", "编辑权限4", "编辑权限5"};

    /**
     * aes加密密码
     */
    public static final String KEY = "wid94jd8538d4gi3";

    /**
     * aes加密偏移量
     */
    public static final String IV = "8foeks5d83kc5dor";

    /**
     * 逻辑删除标识
     */
    public static final Integer REMOVE = 1;

    /**
     * 逻辑正常标识
     */
    public static final Integer NORMAL = 0;

    /**
     * 维表类型: 0任务类型,1任务状态,2任务来源,3任务事件,
     * 4推送对象,5参与对象,6性别，7本异网，8城市，
     * 9产品，10电信员工,11加载渠道,12问题类型,13问题部门,14预警类型,15问题类型
     * 16消息类型
     */
    public static final String[] WBTYPE = {
            "taskType", "taskState", "taskSource", "taskEvent", "taskPushObject",
            "taskPartakeObject", "sex", "ifLocalNetwork", "city", "product",
            "teclonogyStaffValue", "taskChannel", "problemState", "problemDept",
            "warningType", "problemType","msgType","consumePerMonth"
    };

    /**
     * 文件类型
     */
    public static final String[] FILEEXTS = {".png", ".jpg", ".jpeg", ".bmp", ".PNG", ".JPG", ".JPEG", ".BMP"};

    /**
     * @explain : 集团对接签名串
     * @Author : chao
     * @CreationDate : 2019/3/26
     */
    public static final String SIGN = "58c53c6568c55d675637fe392b6088d8";

    /**
     * @explain : 消息正文
     * @Author : chao
     * @CreationDate : 2019/4/3
     */
    public static final String[] MSG =
    {
            "终于等到您啦！您可以通过“个人中心”完善资料，获得积分哦~",
            "您可以通过“个人中心”完善资料，获得积分哦~",
            "您已经{0}个月没有领取任务了，快来做任务，奖励积分换好礼。",
            "您已成功领取{0}任务,请在规定时间内完成哦~",
            "您领取的{0}任务还有{1}天，即将结束,请您按时完成哦~",
            "您领取的{0}任务未能按时完成{1}~",
            "恭喜您，完成任务，{0}积分已到账~",
            "恭喜您，积分已达{0}分，升级啦~",
            "您的密码于{0}进行修改，请妥善保管。",
            "您在{0}，进行{1}积分兑换，扣减{1}积分，当前积分{2}分。",
            "这里有一个新任务，等待您的领取，任务链接：{0}"
    };
}
