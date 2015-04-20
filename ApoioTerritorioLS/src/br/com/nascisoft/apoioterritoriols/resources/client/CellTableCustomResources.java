package br.com.nascisoft.apoioterritoriols.resources.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.CellTable.Resources;
import com.google.gwt.user.cellview.client.CellTable.Style;

public interface CellTableCustomResources extends Resources {
	
	public static final CellTableCustomResources INSTANCE = GWT.create(CellTableCustomResources.class);
	
    @Source({Style.DEFAULT_CSS, "/css/CustomCellTable.css"})
    Style cellTableStyle();
    
    public interface CustomCellTableStyle extends Style {
	}
}
