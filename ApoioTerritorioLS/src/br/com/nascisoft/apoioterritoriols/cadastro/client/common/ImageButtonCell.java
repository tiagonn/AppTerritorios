package br.com.nascisoft.apoioterritoriols.cadastro.client.common;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Image;

public class ImageButtonCell extends ButtonCell{

    @Override
    public void render(com.google.gwt.cell.client.Cell.Context context, 
            String value, SafeHtmlBuilder sb) {
    	Image imagem = new Image(value);
    	imagem.setStyleName("imageButton");
        SafeHtml html = SafeHtmlUtils.fromTrustedString(imagem.toString());
        sb.append(html);
    }
}