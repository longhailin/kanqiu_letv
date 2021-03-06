package com.letv.watchball.parser;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import com.letv.http.bean.LetvBaseBean;
import com.letv.http.parse.LetvMainParser;

/**
 * 移动端接口，解析器父类 ｛ header:{status:"x"}, body:{...} ｝ 针对返回模式这样的解析
 * */
public abstract class LetvMobileParser<T extends LetvBaseBean> extends
		LetvMainParser<T, JSONObject> {

	/**
	 * 接口信息节点
	 * */
	protected final String HEADER = "header";
	/**
	 * 接口返回状态：1-正常，2-无数据，3-服务异常
	 * */
	protected final String STATUS = "status";
	/**
	 * 接口时间轴
	 * */
	protected final String MARKID = "markid";
	/**
	 * 接口返回数据节点
	 * */
	protected final String BODY = "body";

	public interface STATE {
		public int NORMAL = 1;
		public int NODATA = 2;
		public int EXCEPTION = 3;
		public int NOUPDATE = 4;
	}

	/**
	 * 借口状态
	 * */
	protected int status;

	/**
	 * 接口时间轴
	 * */
	private String markid;

	public LetvMobileParser() {
		super();
	}

	public LetvMobileParser(int from) {
		super(from);
	}

	@Override
	protected final boolean canParse(String data) {
		try {
			Log.d("feedbackdata", data);
			JSONObject object = new JSONObject(data);
			if (!object.has(HEADER)) {
				return false;
			}
			JSONObject headJson = object.getJSONObject(HEADER);
			status = getInt(headJson, STATUS);

			if (status == STATE.NORMAL) {
				if (has(headJson, MARKID)) {
					markid = getString(headJson, MARKID);
				}
				return true;
			}

			if (status == STATE.NOUPDATE) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected JSONObject getData(String data) throws JSONException {
		JSONObject object = null;
		if (status == STATE.NORMAL) {
			object = new JSONObject(data);
			object = getJSONObject(object, BODY);
		} else if (status == STATE.NOUPDATE) {
			object = new JSONObject(getLocationData());
		}

		return object;
	}

	/**
	 * 加载本地数据，需要缓存数据的解析器需要实现
	 * */
	protected String getLocationData() {
		return null;
	};

	public String getMarkId() {
		return markid;
	}

	public boolean isNewData() {
		return status == STATE.NORMAL;
	}
}
