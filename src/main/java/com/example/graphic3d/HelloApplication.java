package com.example.graphic3d;

import box.drawingBox;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class HelloApplication extends Application {
    private static final double WIDTH=800,HEYGIHT=600;
    private double anchorX,anchorY;
    private  double anchorAngleX=0;
    private  double anchorAngleY=0;
    private final DoubleProperty angleX=new SimpleDoubleProperty(0);
    private final DoubleProperty angleY=new SimpleDoubleProperty(0);

    FileInputStream fileInputStream;
   Image image;
    SmartGroup group=new SmartGroup();
    int radius=50;
    drawingBox drawingBox=new drawingBox();
    public void start(Stage stage) throws IOException {
        Sphere sphere=new Sphere(20);
      Box box=permereBox();

        group.getChildren().add(sphere);
        group.getChildren().addAll(ambientLightSource());
      //  group.getChildren().add(new AmbientLight());

       Camera camera=new PerspectiveCamera(true);
       camera.setFarClip(1);
       camera.setNearClip(1000);
       camera.translateZProperty().set(-400);
        Scene scene=new Scene(group,WIDTH,HEYGIHT);
        scene.setCamera(camera);
        scene.setFill(Color.SILVER);
        group.translateXProperty().set(0);
        group.translateYProperty().set(0);
       group.translateZProperty().set(0);
       //camera.translateZProperty().set(group.getTranslateZ());


        initMouseController(group,scene,stage);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()){
                case W:
                   group.translateZProperty().set(group.getTranslateZ()+100);
                    break;
                case S:
                    group.translateZProperty().set(group.getTranslateZ()-100);
                    break;
                case Q:
                    group.roteteByX(10);
                    break;
                case E:
                    group.roteteByX(-10);
                    break;
                case NUMPAD6:
                    group.roteteByY(10);
                    break;
                case NUMPAD4:
                    group.roteteByY(-10);
                    break;
            }
        });
        stage.setTitle("Sfera");
        stage.setScene(scene);
        stage.show();

        AnimationTimer animationTimer=new AnimationTimer() {
            @Override
            public void handle(long l) {
                pointLight.setRotate(pointLight.getRotate()+1);
                pointLight.setColor(Color.DARKCYAN);
            }
        };
        animationTimer.start();
    }
    private final PointLight pointLight=new PointLight();
    private Node[] ambientLightSource() {
//        AmbientLight ambientLight=new AmbientLight();
//        ambientLight.setColor(Color.OLIVE);
//        return ambientLight;


        pointLight.setColor(Color.RED);
        pointLight.getTransforms().add(new Translate(0,-50,100));
        pointLight.setRotationAxis(Rotate.X_AXIS);
      //  pointLight.setRotationAxis(Rotate.Y_AXIS);
        Sphere sphere=new Sphere(2);
        Sphere sphere1=new Sphere(2);
        Sphere sphere2=new Sphere(2);
        Sphere sphere3=new Sphere(2);
        Sphere sphere4=new Sphere(2);
        Sphere sphere5=new Sphere(2);
        Sphere sphere6=new Sphere(2);
        Sphere sphere7=new Sphere(2);
        Sphere sphere8=new Sphere(2);

        sphere.getTransforms().setAll(pointLight.getTransforms());
        sphere.rotateProperty().bind(pointLight.rotateProperty());
        sphere.rotationAxisProperty().bind(pointLight.rotationAxisProperty());

        sphere1.getTransforms().setAll(pointLight.getTransforms());
        sphere1.rotationAxisProperty().bind( pointLight.rotationAxisProperty());
        sphere1.rotateProperty().bind(pointLight.rotateProperty());

        sphere2.getTransforms().setAll(pointLight.getTransforms());
        sphere2.rotationAxisProperty().set(Rotate.Z_AXIS);
        sphere2.rotateProperty().bind(pointLight.rotateProperty());

        sphere3.translateXProperty().set(group.getTranslateZ()+10);
        sphere3.getTransforms().setAll(pointLight.getTransforms());
        sphere3.rotationAxisProperty().bind( pointLight.rotationAxisProperty());
        sphere3.rotateProperty().bind(pointLight.rotateProperty());

        sphere4.translateXProperty().set(group.getTranslateY()+30);
        sphere4.getTransforms().setAll(pointLight.getTransforms());
        sphere4.rotationAxisProperty().set(new Point3D(2,1,2));
        sphere4.rotateProperty().bind(pointLight.rotateProperty());

        sphere5.translateXProperty().set(group.getTranslateX()+40);
        sphere5.getTransforms().setAll(pointLight.getTransforms());
        sphere5.rotationAxisProperty().set(new Point3D(3,3,1));
        sphere5.rotateProperty().bind(pointLight.rotateProperty());

        return new Node[]{pointLight,sphere,sphere1,sphere2,sphere3,sphere4,sphere5};
    }

    private Box permereBox() {
        PhongMaterial material=new PhongMaterial();
        Box box=new Box(100,20,50);
//        Image image=new Image(getClass().getResourceAsStream("registon.png.jpg"));
//        material.setDiffuseMap(image);
        box.setMaterial(material);
        box.setEffect(new Reflection());
        return box;
    }

    private void initMouseController(SmartGroup group, Scene scene, Stage stage) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate=new Rotate(0,Rotate.X_AXIS),
                yRotate=new Rotate(0,Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                anchorX=mouseEvent.getSceneX();
                anchorY=mouseEvent.getSceneX();
                anchorAngleX=angleX.get();
                anchorAngleY=angleY.get();
            }
        });
        scene.setOnMouseDragged(event->{
            angleX.set(anchorAngleX-(anchorAngleY-event.getSceneY()));
            angleY.set(anchorAngleY+anchorAngleX-event.getSceneX());
        });
        stage.addEventHandler(ScrollEvent.SCROLL,scrollEvent -> {
            double delta= scrollEvent.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ()-delta);
        });
    }


    public static void main(String[] args) {
        launch();
    }
    class SmartGroup extends Group{
        Rotate rotate;
        Transform transform=new Rotate();
        void roteteByX(double v){
            rotate=new Rotate(v,Rotate.X_AXIS);
          transform=transform.createConcatenation(rotate);
            this.getTransforms().clear();
            this.getTransforms().add(transform);
        }
        void roteteByY(double v){
            rotate=new Rotate(v,Rotate.Y_AXIS);
            transform=transform.createConcatenation(rotate);
            this.getTransforms().clear();
            this.getTransforms().add(transform);
        }
    }
}