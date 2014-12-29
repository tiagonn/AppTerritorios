package br.com.nascisoft.apoioterritoriols.admin.client.view;



public interface AdminBackupView extends AdminView {
	
	public interface Presenter extends AdminView.Presenter {
		void dispararBackup(String destinatarios, String tipo);
		void obterUploadAction();
	}
	
	void setPresenter(Presenter presenter);
	void setAction(String action);
}
