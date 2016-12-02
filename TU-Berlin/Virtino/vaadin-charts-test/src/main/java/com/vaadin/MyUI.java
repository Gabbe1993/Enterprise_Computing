package com.vaadin;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;
import java.util.Random;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Push
@Theme("mytheme")
public class MyUI extends UI {

    private VerticalLayout layout = new VerticalLayout();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

        Configuration lineConf = makeChart(ChartType.LINE, "Random Lines");
        Configuration pieConf = makeChart(ChartType.PIE, "I like pie chart");
        Configuration barConf = makeChart(ChartType.BAR, "bar");

     //   lineConf.getChart().seto

        DataSeries series = new DataSeries();

        pieConf.addSeries(series);
        barConf.addSeries(series);

        final TextField xField = new TextField("X value");
        final TextField yField = new TextField("Y value");

        Button button = new Button("Add point to pie chart");
        button.addClickListener(e -> {
            try {
                Number year = Integer.parseInt(yField.getValue());
                Number pop = Integer.parseInt(xField.getValue());
                series.add(new DataSeriesItem(year, pop));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            barConf.addSeries(series);
            pieConf.addSeries(series);
        });
        runConfiguration(pieConf, new DataSeries(), 5000);
        runConfiguration(barConf, new DataSeries(), 3000);
        runConfiguration(lineConf, new DataSeries(), 1000);

        layout.addComponent(xField);
        layout.addComponent(yField);
        layout.addComponent(button);

        setContent(layout);
    }

    private void runConfiguration(Configuration conf, DataSeries series, int sleepTime) {
        final double[] axis = {0, 0};
        Random random = new Random();
        boolean isPie = conf.getChart().getType() == ChartType.PIE;

        conf.addSeries(series);

        new Thread(() -> {
            System.out.println("running thread for " + conf.getChart().getType());

            while (true) {
                // Init done, update the UI after doing locking
               access(() -> {
                    // Here the UI is locked and can be updated
                    if (isPie) {
                        series.add(new DataSeriesItem(random.nextInt(100), random.nextInt(100)));
                    } else {
                        series.add(new DataSeriesItem(axis[0]++, axis[1] += random.nextDouble() - 0.5));
                    }
                    conf.addSeries(series);
               //     conf.getChart().dr
                });
                try {
                    Thread.sleep(sleepTime);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }).start();
    }

    private Configuration makeChart(ChartType type, String title) {
        Chart chart = new Chart(type);

        Configuration conf = chart.getConfiguration();

        conf.setTitle(title);
        conf.getChart().setType(type);
        conf.getyAxis().setTitle("Population");
        conf.getxAxis().setTitle("Year");

        layout.addComponent(chart);

        return conf;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
