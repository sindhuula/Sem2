# Iteration 2 Evaluation - Group 13

**Evaluator: [Joseph Min](mailto:jmin9@jhu.edu)**
**Evaluator: [Hari Menon](mailto:hmenon@cs.jhu.edu)**


## Positive Points:
Your screenshots are still amazing (love the GIFs). The feature list is extensive and easy to read, and the use-cases are perfectly exhaustive. Good job on the communication protocol.

## Things to Improve:
- You're missing some of the necessary numbers on your relations in your UML class diagrams, specifically the Front-End one.

- It is worth noting that REST apis have a certain convention to them. For example to create a new game, you typically `POST` to an endpoint like `/battleship/v1/games`. You donot have to create a specific endpoint like `/battleship/v1/games/new`. Generally speaking, endpoints should almost always be based on /nouns/ and not verbs. So instead of something like `/battleship/v1/games/respond/{gameID}`, it may be preferable to use just PUT to `/battleship/v1/games/{gameID}` to join a game. This is not a hard-and-fast rule. But you may want to take a look at some REST api guidelines.

*(-2 pt)*

## Additional Notes:
Overall, it looks like you are off to a good start, and are in a great place to start building your app. I would encourage all of you to do a simple Android tutorial/walkthrough to learn how to use Android Studio (or your preferred IDE), so that you don't get bogged down by syntactical details and can easily implement your ideas. This is, of course, optional, but I think it would be pretty helpful. If you want to start building your app without that though, that's fine too!

**Grade: (98/100)**
