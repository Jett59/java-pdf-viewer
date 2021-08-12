set -e
set +x

mvn clean package

jpackage \
--type msi \
-d ./target/installer \
--name "PdfViewer" \
--input ./target/app \
--app-version "0.1" \
--copyright "2021 Jett Thompson" \
--vendor "Jett Thompson" \
--main-jar java-pdf-viewer.jar \
--module-path ./target/app/lib \
--add-modules javafx.controls,java.logging \
--win-shortcut \
--win-menu \
--win-dir-chooser \
--win-upgrade-uuid "afd078bf-cc02-47f8-8cba-c5dbdf33e1fe"
