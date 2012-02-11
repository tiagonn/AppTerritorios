package br.com.nascisoft.apoioterritoriols.admin.client.view;



public interface AdminBackupView extends AdminView {
	
	public interface Presenter extends AdminView.Presenter {
	}
	
	void setPresenter(Presenter presenter);
}
