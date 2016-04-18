package cn.com.reachmedia.rmhandle.model;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/18 上午11:53
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/18          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ApiResponse<T,M> {
    public String state;
    public int version;
    public T data;
    public M details;

    public int code;
}
