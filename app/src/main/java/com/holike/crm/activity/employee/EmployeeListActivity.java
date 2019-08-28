package com.holike.crm.activity.employee;

import com.holike.crm.R;
import com.holike.crm.base.BasePresenter;
import com.holike.crm.base.MyFragmentActivity;
import com.holike.crm.fragment.employee.EmployeeListFragment;

/*员工列表*/
public class EmployeeListActivity extends MyFragmentActivity {
    @Override
    protected BasePresenter attachPresenter() {
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_employeelist;
    }

    @Override
    protected void init() {
        super.init();
        startFragment(new EmployeeListFragment());
    }
}
