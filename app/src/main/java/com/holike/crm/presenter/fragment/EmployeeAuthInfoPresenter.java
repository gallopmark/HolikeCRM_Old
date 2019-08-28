package com.holike.crm.presenter.fragment;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;

import com.gallopmark.recycler.adapterhelper.CommonAdapter;
import com.holike.crm.R;
import com.holike.crm.base.BasePresenter;
import com.holike.crm.bean.EmployeeDetailBean;
import com.holike.crm.bean.MultiItem;
import com.holike.crm.fragment.OnFragmentDataChangedListener;
import com.holike.crm.local.LocalDataSource;
import com.holike.crm.model.fragment.EmployeeModel;
import com.holike.crm.view.fragment.EmployeeAuthInfoView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAuthInfoPresenter extends BasePresenter<EmployeeAuthInfoView, EmployeeModel> {

    private List<MultiItem> items = new ArrayList<>();
    private AuthInfoAdapter adapter;
    private List<EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean> resultBeans = new ArrayList<>();

    private static class AuthInfoAdapter extends CommonAdapter<MultiItem> {

        private OnItemCheckChangedListener onItemCheckChangedListener;

        void setOnItemCheckChangedListener(OnItemCheckChangedListener onItemCheckChangedListener) {
            this.onItemCheckChangedListener = onItemCheckChangedListener;
        }

        AuthInfoAdapter(Context context, List<MultiItem> items) {
            super(context, items);
        }

        public void addAll(List<EmployeeDetailBean.EmployeeAuthInfoBean> beans) {
            mDatas.clear();
            mDatas.add(new EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean(mContext.getString(R.string.employee_settings_tips1), true));
            for (EmployeeDetailBean.EmployeeAuthInfoBean bean : beans) {
                mDatas.add(bean);
                if (bean.getArr2() != null && !bean.getArr2().isEmpty()) {
                    for (int i = 0; i < bean.getArr2().size(); i++) {
                        EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean arrBean = bean.getArr2().get(i);
                        if (i < bean.getArr2().size() - 1) {
                            arrBean.setShowLine(true);
                        } else {
                            arrBean.setShowLine(false);
                        }
                        mDatas.add(arrBean);
                    }
                }
            }
            notifyDataSetChanged();
        }

        void setSelectItems(List<EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean> pArrBeans) {
            for (EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean pArrBean : pArrBeans) {
                int index = mDatas.indexOf(pArrBean);
                if (index >= 0 && index < mDatas.size()) {
                    mDatas.set(index, pArrBean);
                }
            }
            int select = isAllOpen() ? 1 : 0;
            ((EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean) mDatas.get(0)).setIsSelect(select);
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            return mDatas.get(position).getItemType();
        }

        @Override
        protected int bindView(int viewType) {
            if (viewType == 1) {
                return R.layout.item_employee_authinfo1;
            }
            return R.layout.item_employee_authinfo2;
        }

        @Override
        public void onBindHolder(RecyclerHolder holder, MultiItem item, int position) {
            int itemType = holder.getItemViewType();
            if (itemType == 1) {
                EmployeeDetailBean.EmployeeAuthInfoBean bean = (EmployeeDetailBean.EmployeeAuthInfoBean) item;
                holder.setText(R.id.mSmallTipsTv, bean.getName());
            } else {
                EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean bean = (EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean) item;
                holder.setText(R.id.mTipsTv, bean.getActionName());
                SwitchCompat mSwitch = holder.obtainView(R.id.mSwitch);
                holder.setVisibility(R.id.vUnderLine, bean.isShowLine());
                mSwitch.setOnCheckedChangeListener(null);
                mSwitch.setChecked(bean.getIsSelect() == 1);
                mSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                    if (bean.isHeader()) {
                        onHeaderChecked(isChecked);
                    } else {
                        if (isChecked) {
                            bean.setIsSelect(1);
                        } else {
                            bean.setIsSelect(0);
                        }
                        mDatas.set(position, bean);
                        onItemCheckedChanged();
                    }
                    if (onItemCheckChangedListener != null) {
                        onItemCheckChangedListener.onItemCheckChanged(getSelectItems());
                    }
                });
            }
        }

        private List<EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean> getSelectItems() {
            List<EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean> list = new ArrayList<>();
            for (MultiItem item : mDatas) {
                if (item instanceof EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean) {
                    EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean arrBean = (EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean) item;
                    if (!arrBean.isHeader() && arrBean.getIsSelect() == 1) {
                        list.add(arrBean);
                    }
                }
            }
            return list;
        }

        private void onHeaderChecked(boolean isChecked) {
            int select = isChecked ? 1 : 0;
            for (MultiItem item : mDatas) {
                if (item instanceof EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean) {
                    ((EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean) item).setIsSelect(select);
                }
            }
            notifyDataSetChanged();
        }

        private void onItemCheckedChanged() {
            if (isAllOpen()) {
                ((EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean) mDatas.get(0)).setIsSelect(1);
                notifyItemChanged(0);
            } else if (isAllClosed()) {
                ((EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean) mDatas.get(0)).setIsSelect(0);
                notifyItemChanged(0);
            }
        }

        private boolean isAllOpen() {
            boolean isAllOpen = true;
            for (MultiItem item : mDatas) {
                if (item instanceof EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean) {
                    EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean bean = (EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean) item;
                    if (!bean.isHeader() && bean.getIsSelect() != 1) {
                        isAllOpen = false;
                        break;
                    }
                }
            }
            return isAllOpen;
        }

        private boolean isAllClosed() {
            boolean isAllClosed = true;
            for (MultiItem item : mDatas) {
                if (item instanceof EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean) {
                    EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean bean = (EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean) item;
                    if (!bean.isHeader() && bean.getIsSelect() == 1) {
                        isAllClosed = false;
                        break;
                    }
                }
            }
            return isAllClosed;
        }

        public interface OnItemCheckChangedListener {
            void onItemCheckChanged(List<EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean> mSelectItems);
        }
    }

    public void setAdapter(RecyclerView recyclerView, @NonNull OnFragmentDataChangedListener onFragmentDataChangedListener) {
        adapter = new AuthInfoAdapter(recyclerView.getContext(), items);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemCheckChangedListener(mSelectItems -> onFragmentDataChangedListener.onFragmentDataChanged(resultBeans.size() != mSelectItems.size() || !resultBeans.containsAll(mSelectItems)));
    }

    public void addAll(List<EmployeeDetailBean.EmployeeAuthInfoBean> beans) {
        if (adapter == null) return;
        adapter.addAll(beans);
    }

    public void setSelectItems(List<EmployeeDetailBean.EmployeeAuthInfoBean> beans) {
        if (adapter == null) return;
        for (EmployeeDetailBean.EmployeeAuthInfoBean bean : beans) {
            List<EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean> arrBeans = bean.getArr2();
            if (arrBeans != null && !arrBeans.isEmpty()) {
                for (EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean arrBean : arrBeans) {
                    if (arrBean.getIsSelect() == 1) {
                        resultBeans.add(arrBean);
                    }
                }
            }
        }
        adapter.setSelectItems(resultBeans);
    }

    public List<EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean> getSelectItems() {
        if (adapter == null) return new ArrayList<>();
        return adapter.getSelectItems();
    }

    public void getAuthInfo(Context context) {
        List<EmployeeDetailBean.EmployeeAuthInfoBean> infoBeans = LocalDataSource.getAuthInfo(context);
        if (infoBeans != null) {
            if (getView() != null) {
                getView().onGetAuthInfo(infoBeans);
            }
            return;
        }
        model.getAuthInfo(new EmployeeModel.OnGetAuthInfoCallback() {
            @Override
            public void onLoading() {
                if (getView() != null) getView().onShowLoading();
            }

            @Override
            public void onGetAuthInfo(List<EmployeeDetailBean.EmployeeAuthInfoBean> infoBeans) {
                LocalDataSource.saveAuthInfo(context, infoBeans);
                if (getView() != null) getView().onGetAuthInfo(infoBeans);
            }

            @Override
            public void onGetAuthInfoFailure(String message) {
                if (getView() != null) getView().onGetAuthInfoFail(message);
            }

            @Override
            public void onLoadingEnd() {
                if (getView() != null) getView().onHideLoading();
            }
        });
    }
}
