package xzh.com.listviewhover.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;

import xzh.com.listviewhover.R;
import xzh.com.listviewhover.utils.Utils;

public class SideBar extends View {

	private SectionIndexer sectionIndexter = null;
	private ListView list;
	private int m_nItemHeight = 38;
	private Context context;
	private Toast toast;
	private TextView mDialogText;
	private WindowManager mWindowManager;
	private ArrayList<Character> index;
	private WindowManager.LayoutParams lp;
	private PopupWindow mPopupWindow;

	public SideBar(Context context) {
		super(context);
		init(context);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);

	}

	public void setIndex(ArrayList<Character> index1) {
		this.index = index1;
		if (index == null) {
			index = new ArrayList<Character>();
		}

		this.invalidate();
	}

	private void init(Context context) {
		this.context = context;
		index = new ArrayList<Character>();
		Collections.addAll(index, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W', 'X', 'Y', 'Z');

		mDialogText = (TextView) LayoutInflater.from(context).inflate(
				R.layout.list_position, null);
		mDialogText
				.setTextColor(getResources().getColor(android.R.color.white));

	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public void setListView(ListView _list) {
		list = _list;
		sectionIndexter = (SectionIndexer) _list.getAdapter();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		int i = (int) event.getY();
		int idx = i / m_nItemHeight;
		if (idx >= index.size()) {
			idx = index.size() - 1;
		} else if (idx < 0) {
			idx = 0;
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			if (sectionIndexter == null) {
				sectionIndexter = (SectionIndexer) list.getAdapter();
			}
			int position = sectionIndexter
					.getPositionForSection(index.get(idx));
			if (position == -1) {
				return true;
			}
			list.setSelection(position);
			showPopup(index.get(idx) + "");

		} else {
			dismissPopup();
		}
		return true;
	}

	private void showPopup(String item) {
		if (mPopupWindow == null) {
			mPopupWindow = new PopupWindow(mDialogText,
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		}
		mDialogText.setText(item);
		if (mPopupWindow.isShowing()) {
			mPopupWindow.update();
		} else {
			mPopupWindow.showAtLocation(getRootView(),
					Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
		}
	}

	private void dismissPopup() {
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
		}
	}


	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(0xFF666666);
		paint.setTextSize(Utils.dip2px(getContext(), 16));
//		paint.setTextSize(20);
		paint.setAntiAlias(true);
		Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		paint.setTypeface(font);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setTextAlign(Paint.Align.CENTER);
		float widthCenter = getMeasuredWidth() / 2;
		m_nItemHeight = getMeasuredHeight()/ 26;
		for (int i = 0; i < index.size(); i++) {
			canvas.drawText(String.valueOf(index.get(i)), widthCenter,
					m_nItemHeight + (i * m_nItemHeight), paint);
		}
		this.invalidate();
		super.onDraw(canvas);
	}

	// 防止 window leak；
	public void removeTextDialog() {
		// mWindowManager.removeView(mDialogText);
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		dismissPopup();
		return super.onSaveInstanceState();
	}


}