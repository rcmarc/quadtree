package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.app.config.AppConfig;
import com.github.rcmarc.quadtree.app.interfaces.CircleFactory;
import com.github.rcmarc.quadtree.core.Point2D;
import javafx.scene.input.MouseEvent;

public class MouseEventCircleMapper {

    private CircleFactory circleFactory;

    public MouseEventCircleMapper(CircleFactory circleFactory) {
        this.circleFactory = circleFactory;
    }

    public MouseEventCircleMapper() {
        this.circleFactory = AppConfig.DEFAULT_CONFIG.getCircleFactory();
    }

    Point2DCircle map(MouseEvent evt) {
        Point2D point = new Point2D(evt.getSceneX(), evt.getSceneY(), AppConfig.DEFAULT_CONFIG.getRadius());
        return circleFactory.buildCircle(point);
    }

}
