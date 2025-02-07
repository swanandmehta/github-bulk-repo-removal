# Github Bulk Repo Removal

### Story
Over the last few years, I created 34 repositories, most of which were one-off projects, proofs of concept (POCs), or temporary experiments. Eventually, I wanted to clean up and remove all of these unnecessary repositories in bulk, without having to manually delete each one. So, I built a small utility to help me automate the process.

### How to Use

1. **Clone the Repository**
   Start by cloning this repository to your local machine.

2. **Configure the Application**
   Open the `app.properties` file and update the following configurations:
   - **Username**: Replace the `org.liberty.grbr.username` with your GitHub username.
   - **Token**: Add your GitHub personal access token with the necessary permissions (explained below).
   - **Ignore List**: If there are repositories you want to retain, add them to the `org.liberty.grbr.ignore.list` property. This will prevent those repositories from being deleted.

3. **Run the Utility**
   Once everything is set up, simply run the `org.liberty.grbr.Main` class to start the bulk removal process.

### FAQ

#### 1. What if I don't want to delete all of my repositories?
If you wish to preserve certain repositories, simply list their names under the `org.liberty.grbr.ignore.list` in `app.properties`. For example:
```properties
org.liberty.grbr.ignore.list=repo1,repo2,repo3
```
This will ensure those repositories are not deleted during the bulk removal process.

#### 2. Where can I find my GitHub username?
Your GitHub username is part of the URL of your profile. For example, if your GitHub URL is `https://github.com/johndoe`, then your username is `johndoe`. You will need to update the following property in `app.properties`:
```properties
org.liberty.grbr.username=johndoe
```

#### 3. Where can I get a GitHub token?
To generate a GitHub token, go to [GitHub's Personal Access Tokens page](https://github.com/settings/tokens). Make sure to grant the token the following scopes:
- `repo`: To manage your repositories
- `delete_repo`: To delete repositories

Once you have generated the token, paste it into the `app.properties` file.

Happy coding.
