## 1. Fix click mapping

- [x] 1.1 In `GameplayScreen.handleInput()`, replace `camera.unproject(touchPos)` with `viewport.unproject(touchPos)`
- [x] 1.2 Verify the game compiles and run — test clicking at various positions at the default window size, a resized window, and fullscreen
