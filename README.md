
# Hunters  [🛠 WORK-IN-PROGRESS 🛠]
 Hunters is Unofficial Android client for [Product Hunt](https://www.producthunt.com/)
 
 
 ## Android development
 
 Hunters is an app which attempts to use the latest cutting edge libraries and tools. As a summary:
 
 * Entirely written in [Kotlin](https://kotlinlang.org/)
 * Uses [Kotlin Croutines ](https://kotlinlang.org/docs/reference/coroutines-overview.html)
 * Uses Uses many of the [Architecture Components](https://developer.android.com/topic/libraries/architecture/)
 * Uses [Jekpack Compose](https://developer.android.com/jetpack/compose)
 * Uses [hilt](https://dagger.dev/hilt/) for dependency injection
 
 ## Development setup
 
 First off, you require the Android Studio Arctic Fox Canary 6 to be able to build the app.
 
 ### Code style

 This project uses [ktlint](https://github.com/pinterest/ktlint), provided via the [ktlint-gradle](https://github.com/jlleitschuh/ktlint-gradle) gradle plugin.

 #### ktlint- gradle config

 1. Apply ktlint code style to Android Studio's code formatter
 ```
    ./gradlew ktlintApplyToIdea
 ```

 2.   Make check before commit
 ```
    ./gradlew addKtlintCheckGitPreCommitHook
 ```

 If you find that one of your pull reviews does not pass the CI server check due to a code style conflict, you can
 easily fix it by running: `./gradlew ktlintFormat`, or running Android Studio's code formatter.

 
 ### API keys
 
 You need to supply your product hunt developer token
 When you obtain the token, you can provide them to the app by putting the following in the
 `gradle.properties` file in your user home:
 
 ```
PROCUCT_HUNT_DEVELOPER_TOKEN=<your product hunt developer token>
 ```
 
 On Linux/Mac that file is typically found at `~/.gradle/gradle.properties`.
 
 ## Contributions
 
 If you've found an error in this sample, please file an issue.
 
 Patches are encouraged, and may be submitted by forking this project and
 submitting a pull request. Since this project is still in its very early stages,
 if your change is substantial, please raise an issue first to discuss it.
 
 ## License
 
 ```
 Copyright 2017 Mohammed Boukadir

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
  
  http://www.apache.org/licenses/LICENSE-2.0
  
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
  
 ```
