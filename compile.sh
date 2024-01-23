#!/bin/bash -ex

clear
mvn -e -q clean
mvn -e -q compile
mvn exec:java -e -q -Dprism.order=sw -Dexex.mainClass=cs1302.gallery.GalleryDriver
