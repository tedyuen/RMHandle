package cn.com.reachmedia.rmhandle.ui.fragment;


import cn.com.reachmedia.rmhandle.model.TaskDetailModel;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/6 下午2:43
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/6          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public abstract class TaskInfoBaseFragment extends BaseFragment {

    public abstract void updateData(TaskDetailModel model);

    public abstract void onFailDisplay(String errorMsg);
}
