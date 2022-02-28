module org.openjfx.ce2006project_maven {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.base;
	requires org.json;
	
    exports org.openjfx.ce2006project_maven;
    opens org.openjfx.ce2006project_maven;
}