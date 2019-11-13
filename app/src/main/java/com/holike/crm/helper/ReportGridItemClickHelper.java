package com.holike.crm.helper;

import com.holike.crm.activity.analyze.ActiveMarketActivity;
import com.holike.crm.activity.analyze.ActiveMarketRankActivity;
import com.holike.crm.activity.analyze.BuildStoreActivity;
import com.holike.crm.activity.analyze.CupboardActivity;
import com.holike.crm.activity.analyze.CustomerSatisfactionActivity;
import com.holike.crm.activity.analyze.DealerRankActivity;
import com.holike.crm.activity.analyze.FastLiveActivity;
import com.holike.crm.activity.analyze.InstallEvaluateActivity;
import com.holike.crm.activity.analyze.MonthPkActivity;
import com.holike.crm.activity.analyze.NetActivity;
import com.holike.crm.activity.analyze.NewRetailActivity;
import com.holike.crm.activity.analyze.OnlineAttractReportActivity;
import com.holike.crm.activity.analyze.OrderRankingActivity;
import com.holike.crm.activity.analyze.OrderReportActivity;
import com.holike.crm.activity.analyze.OriginalBoardActivity;
import com.holike.crm.activity.analyze.PerformanceActivity;
import com.holike.crm.activity.analyze.ProductTradingActivity;
import com.holike.crm.activity.analyze.TerminalCheckActivity;
import com.holike.crm.activity.analyze.TranslateReportActivity;
import com.holike.crm.activity.analyze.WeekReportActivity;
import com.holike.crm.activity.analyze.WoodenDoorActivity;
import com.holike.crm.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by pony on 2019/10/31.
 * Version v3.0 app报表
 */
public class ReportGridItemClickHelper {

    public static void dealWith(BaseActivity<?, ?> activity, int type) {
        switch (type) {
            case 1:
                MobclickAgent.onEvent(activity, "analyze_order_trend");
                activity.startActivity(WeekReportActivity.class);
                break;
            case 2:
                MobclickAgent.onEvent(activity, "analyze_order_report");
                activity.startActivity(OrderReportActivity.class);
                break;
            case 3:
                MobclickAgent.onEvent(activity, "analyze_order_translate_report");
                activity.startActivity(TranslateReportActivity.class);
                break;
            case 4:
                MobclickAgent.onEvent(activity, "analyze_sign_order_ranking");
                activity.startActivity(OrderRankingActivity.class);
                break;
            case 5:
                MobclickAgent.onEvent(activity, "analyze_product_trading");
                activity.startActivity(ProductTradingActivity.class);
                break;
            case 6:
                MobclickAgent.onEvent(activity, "analyze_build_shop");
                activity.startActivity(BuildStoreActivity.class);
                break;
            case 7:
                activity.startActivity(InstallEvaluateActivity.class);
                break;
            case 8:
                MobclickAgent.onEvent(activity, "analyze_performance");
                activity.startActivity(PerformanceActivity.class);
                break;
            case 9:
                MobclickAgent.onEvent(activity, "analyze_cupboard");
                activity.startActivity(CupboardActivity.class);
                break;
            case 10:
                MobclickAgent.onEvent(activity, "analyze_original_board");
                activity.startActivity(OriginalBoardActivity.class);
                break;
            case 11:
                MobclickAgent.onEvent(activity, "analyze_dealers_ranking");
                activity.startActivity(DealerRankActivity.class);
                break;
            case 12:
                MobclickAgent.onEvent(activity, "analyze_terminal_check");
                activity.startActivity(TerminalCheckActivity.class);
                break;
            case 13:
                MobclickAgent.onEvent(activity, "analyze_new_retail");
                activity.startActivity(NewRetailActivity.class);
                break;
            case 14:
                MobclickAgent.onEvent(activity, "analyze_net");
                activity.startActivity(NetActivity.class);
                break;
            case 15:
                MobclickAgent.onEvent(activity, "analyze_month_pk");
                activity.startActivity(MonthPkActivity.class);
                break;
            case 16:
                MobclickAgent.onEvent(activity, "analyze_active_marketing");
                activity.startActivity(ActiveMarketActivity.class);
                break;
            case 17: //主动营销排行
                MobclickAgent.onEvent(activity, "analyze_active_marketing_ranking");
                activity.startActivity(ActiveMarketRankActivity.class);
                break;
            case 18: //订金交易报表
                MobclickAgent.onEvent(activity, "analyze_fast_live");
                activity.startActivity(FastLiveActivity.class);
                break;
            case 19: //线上引流
                MobclickAgent.onEvent(activity, "analyze_online_attract_report");
                activity.startActivity(OnlineAttractReportActivity.class);
                break;
            case 20: //木门业绩报表
                activity.startActivity(WoodenDoorActivity.class);
                break;
            case 21: //客户满意度
                activity.startActivity(CustomerSatisfactionActivity.class);
                break;
        }
    }
}
