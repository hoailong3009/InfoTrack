Guideline: How to Run Tests (src/test/java)
1. Prerequisites
• Installed JDK 17 (or JDK 11).
• Codebase structured as:
• project/
• └─ src/
└─ test/java/...

Setup EVM: /resources/config.properties

1. IntelliJ IDEA
Step 1: Download & Install JDK 17
• Download OpenJDK 17 https://www.oracle.com/java/technologies/javase/jdk17-
archive-downloads.html
Step 2: Configure JDK in IntelliJ
1. Open IntelliJ IDEA.
2. 3. 4. 5. 6. Go to File → Project Structure (Ctrl+Alt+Shift+S on Windows/Linux, Cmd+; on macOS).
In the left menu, select Platform Settings → SDKs.
Click the + button → choose JDK.
Navigate to the folder where you installed JDK 17 → click OK.
IntelliJ will index it and show “17” as the version.
Step 3: Set JDK for the Project
1. 2. 3. Still in Project Structure, go to Project Settings → Project.
Under Project SDK, select 17 (jdk-17).
Under Project language level, select SDK default (17 - Pattern matching for switch,
etc.).
4. Apply and close.
Step 4: Verify
• Right-click your test class → Run.
• In the run logs, you should see:
• java version "17.x"
2. Eclipse
Step 1: Download & Install JDK 17
• Download OpenJDK 17 https://www.oracle.com/java/technologies/javase/jdk17-
archive-downloads.html
Step 2: Configure JDK in Eclipse
1. Open Eclipse.
2. 3. 4. 5. 6. 7. Go to Window → Preferences.
Navigate to Java → Installed JREs.
Click Add… → Standard VM → Next.
Browse to your JDK 17 installation directory.
Give it a name (e.g., jdk-17) → Finish.
Check the box next to jdk-17 to make it the default JRE.
Step 3: Set JDK for the Project
1. 2. 3. 4. Right-click your project → Properties.
Select Java Build Path → Tab Libraries.
Click Add Library… → JRE System Library → Alternate JRE.
Pick the jdk-17 you just added.
5. Apply and close.
