# PasteeAPI
This is an API wrapper for paste.ee written in java

# How to use
```java
PasteEEAPI api = new PasteEEAPI();
api.init("your key here");

List<PasteSection> pastes = new ArrayList<>();
for (int i = 0; i < 10; i++) {
  pastes.add(new PasteSection("Test", null, "Testing"));
}
api.submitPaste(true, "test", pastes);

api.useAsUser("your name here", "your password here"); 
// will get the user key from Applications with access if there is one, did not test it without one, i dont know what can happen
ListPasteResponse response = api.listPastes();
```
