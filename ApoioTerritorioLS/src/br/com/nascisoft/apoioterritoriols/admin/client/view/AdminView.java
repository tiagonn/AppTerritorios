package br.com.nascisoft.apoioterritoriols.admin.client.view;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;

public interface AdminView {
	
	public interface Presenter {
		void initView();
		void setTabSelectionEventHandler(SelectionHandler<Integer> handler);
		void selectThisTab();
	}
	
	void showWaitingPanel();
	void hideWaitingPanel();
	void initView();
	void selectThisTab();
	void setTabSelectionEventHandler(SelectionHandler<Integer> handler);
	Widget asWidget();
}
