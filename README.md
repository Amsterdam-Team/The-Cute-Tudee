The-Cute-Tudee v1.0 
**Cute Tudee** is a personal task management app for Android that helps users organize their daily tasks with simplicity and cuteness.  
The app uses **Room** for local storage, **Jetpack Navigation** for seamless screen transitions, and **Koin** for dependency injection.
It follows the **SOLID** principles learned in Kotlin , with a clean and maintainable architecture.
🚀 Features
✅ Functional Requirements
🎯 Task Management
- Create a new task with:
  - Title
  - Description
  - Priority
  - Category
- View task details
- View tasks filtered by selected date
- Delete tasks
- Update task status:
  - "To Do" → "In Progress" → "Done"
🏷️ Categories
- View predefined categories
- Add a new category (with image from device)
- Edit or delete user-created categories
📊 Home Screen
- Shows statistics about today's tasks (e.g., how many are done, pending, etc.)
🌅 Onboarding
- Appears only once on the first app launch
💡 UI & Experience
- Responsive UI that adapts to all screen sizes
- Matches exactly with the provided **Figma design**
- Dark & Light mode toggle (follows system setting)
- Supports **English** and **Arabic** (based on device language)
🧠 Technical Overview
⚙ Architecture
Layered Design:
- ui/: Composables + ViewModels (one ViewModel per screen)
- domain/: Core business models (Entities) + TasksServices abstraction
- data/: Room DAOs + concrete implementations of TasksServices
Unidirectional Flow:
UI → Logic ← Data
(Logic is independent of UI and Data)
Repositories:
Example: AuthenticationRepository, ProjectsRepository, etc.
💾 Data Management
- **Room Database** is used for local persistence
- `TaskDao` and `CategoryDao` handle all DB operations
- Room entities are mapped to **domain entities** by `TasksServices` implementation
🛠️ Dependency Injection
- **Koin** is used to inject all components (ViewModels, Repositories, DAOs)
- Dependency inversion is applied: domain layer is independent of lower layers
- 🎨 Design System
- All colors, typography, paddings, and components are part of a **custom Design System**
- No hardcoded values are used
- `AppTheme.kt`, `Typography.kt` define consistent UI
- 🌐 Localization & Appearance
- Follows **device language setting** (English & Arabic supported)
- Supports **Dark/Light themes**
- Texts are managed via `strings.xml` with translations
💡 Key Concepts & Tools
Kotlin
Coroutines
Room Database
OOP & SOLID
Koin (for Dependency Injection)
📦 Installation
Clone the repo:
git clone https://github.com/Amsterdam-Team/The-Cute-Tudee
