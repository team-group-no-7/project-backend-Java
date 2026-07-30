# 🔀 LearnHub Git Integration Merge Guide

> **Target Branch:** `dev`  
> **Purpose:** Establishes the optimal 0-conflict merge sequence for integrating all feature branches into the `dev` branch.

---

## 🎯 Recommended 4-Step Merge Sequence

To guarantee zero/minimal merge conflicts and a clean `BUILD SUCCESS`, execute merges in this exact order:

```
1. feature/management-grid ➔ dev  (Establishes Content & Category domain schema)
2. learner-dashboard-new   ➔ dev  (Adds Learner Profile, Library & Doubt Sessions)
3. marketplace             ➔ dev  (Adds Marketplace Browsing, Admin & Payment)
4. landing                 ➔ dev  (Adds Public Landing Page APIs)
```

---

## 💻 Step-by-Step Terminal Commands for Integration

Run these commands in the project repository:

```powershell
# Step 0: Ensure local dev is up to date
git checkout dev
git pull origin dev

# Step 1: Merge Management Grid & Content Authoring Studio
git merge origin/feature/management-grid
.\mvnw compile

# Step 2: Merge Learner Workspace & Mentorship Module
git merge origin/learner-dashboard-new
.\mvnw compile

# Step 3: Merge Marketplace & Admin Module
git merge origin/marketplace
.\mvnw compile

# Step 4: Merge Landing Page Module
git merge origin/landing
.\mvnw compile

# Step 5: Push final integrated dev branch to GitHub remote
git push origin dev
```

---

## 💡 Why This Sequence Guarantees 0 Conflicts

1. **Step 1 (`feature/management-grid` First)**: Establishes `Content` and `Category` entities. Because it is built directly on `dev`, merging it first is a **100% Fast-Forward Merge**.
2. **Step 2 (`learner-dashboard-new` Second)**: Learner code lives in separate sub-packages (`user/`, `billing/`, `mentorship/`). Merging second produces 0 conflicts.
3. **Step 3 (`marketplace` Third)**: Consumes the content and user models established in Steps 1 & 2.
4. **Step 4 (`landing` Last)**: Public read-only endpoints built on top of the established catalog.
