package com.subshell.gwt.canvas.client.colorpicker;

import com.google.gwt.dom.client.Style.Clear;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.subshell.gwt.canvas.client.dialog.Dialog;

public class ColorPickerDialog extends Dialog {
	private SaturationLightnessPicker slPicker;
	private HuePicker huePicker;
	private String color;

	@Override
	protected Widget createDialogArea() {
		setText("Selecione a cor");
		
		FlowPanel panel = new FlowPanel();
		
		// the pickers
		slPicker = new SaturationLightnessPicker();
		slPicker.getElement().getStyle().setPadding(10, Unit.PX);
		slPicker.getElement().getStyle().setOverflow(Overflow.HIDDEN);
		slPicker.getElement().getStyle().setCursor(Cursor.DEFAULT);
		slPicker.getElement().getStyle().setFloat(Float.LEFT);
		slPicker.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		panel.add(slPicker);
		huePicker = new HuePicker();
		huePicker.getElement().getStyle().setPadding(10, Unit.PX);
		huePicker.getElement().getStyle().setOverflow(Overflow.HIDDEN);
		huePicker.getElement().getStyle().setCursor(Cursor.DEFAULT);
		huePicker.getElement().getStyle().setFloat(Float.LEFT);
		huePicker.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		panel.add(huePicker);
		
		final FlowPanel colorPanel = new FlowPanel();
		colorPanel.getElement().getStyle().setClear(Clear.BOTH);
		colorPanel.getElement().getStyle().setWidth(230, Unit.PX);
		colorPanel.getElement().getStyle().setMargin(10, Unit.PX);
		colorPanel.getElement().getStyle().setHeight(20, Unit.PX);
		colorPanel.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		
		final Label colorLabel = new Label();
		colorPanel.add(colorLabel);
		
		panel.add(colorPanel);

		// bind saturation/lightness picker and hue picker together
		huePicker.addHueChangedHandler(new IHueChangedHandler() {
			public void hueChanged(HueChangedEvent event) {
				slPicker.setHue(event.getHue());
			}
		});
		
		slPicker.addColorChangedHandler(new IColorChangedHandler() {
			@Override
			public void colorChanged(ColorChangedEvent event) {
				colorPanel.getElement().getStyle().setBackgroundColor("#"+event.getColor());	
				colorLabel.setText(event.getColor());
				try {
					if (Integer.valueOf(event.getColor().substring(0,1)) < 7) {
						colorLabel.getElement().getStyle().setColor("#ffffff");
					} else {
						colorLabel.getElement().getStyle().setColor("#000000");
					}
				} catch (NumberFormatException ex) {
					colorLabel.getElement().getStyle().setColor("#000000");
				}
			}
		});
		
		

		return panel;
	}

	public void setColor(String color) {
		int[] rgb = ColorUtils.getRGB(color);
		int[] hsl = ColorUtils.rgb2hsl(rgb);
		huePicker.setHue(hsl[0]);
		slPicker.setColor(color);
	}
	
	public String getColor() {
		return color;
	}
	
	@Override
	protected void buttonClicked(Widget button) {
		// remember color when "OK" is clicked
		if (button == getOkButton()) {
			color = slPicker.getColor();
		}
		
		close(button == getCancelButton());
	}
}
