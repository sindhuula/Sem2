# search.py
# ---------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util

class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return  [s, s, w, s, w, w, s, w]

def depthFirstSearch(problem):
    """
    Search the deepest nodes in the search tree first.

    Your search algorithm needs to return a list of actions that reaches the
    goal. Make sure to implement a graph search algorithm.

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:

    print "Start:", problem.getStartState()
    print "Is the start a goal?", problem.isGoalState(problem.getStartState())
    print "Start's successors:", problem.getSuccessors(problem.getStartState())
    """
    from util import Stack
    start = problem.getStartState()
    stack = Stack()
    stack.push((start,[],[]))
    while not stack.isEmpty():
            popState, popDirection, visitedStates = stack.pop()
            for successors in problem.getSuccessors(popState):
                state, direction, cost  = successors
                if not state in visitedStates:
                    if problem.isGoalState(state):
                        return popDirection + [direction]
                    stack.push((state,popDirection+ [direction],visitedStates+[popState]))

#util.raiseNotDefined()

def breadthFirstSearch(problem):
    """Search the shallowest nodes in the search tree first."""
    from util import Queue
    start = problem.getStartState()
    queue = Queue()
    queue.push((start,[]))
    visitedStates = []
    while not queue.isEmpty():
        popState,popDirection= queue.pop()
        for successors in problem.getSuccessors(popState):
            state,direction,cost = successors
            if not state in visitedStates:
                if problem.isGoalState(state):
                    return popDirection + [direction]
                queue.push((state, popDirection +[direction]))
                visitedStates.append(state)

    return []

#util.raiseNotDefined()
def iterativeDeepeningSearch(problem):
    depth = 0
    while depth>=0:
        result = depthLimitedSearch(problem,depth)
        if result is not "cutoff" :
            return result
        depth += 1

def depthLimitedSearch(problem,depth):
    return recDepthLimitedSearch(problem.getStartState(),problem,depth,[],[])
def recDepthLimitedSearch(start,problem,depth,path,visited):
   if (problem.isGoalState(start)):
        return (path)
   elif depth == 0:
        return ("cutoff")
   else:
       cutoffReached = False
       for successors in problem.getSuccessors(start):
            state,direction,cost = successors
          # path = path+ [direction]
            if state not in visited:
                  result = recDepthLimitedSearch(state,problem,depth-1,path + [direction], visited + [state])
                  if result == "cutoff":
                        cutoffReached = True
                  elif result is not "failure":
                        return result
       if cutoffReached is True:
                return ("cutoff")
       else:
                return ("failure")

def uniformCostSearch(problem):
    """Search the node of least total cost first."""
    "*** YOUR CODE HERE ***"
    from util import PriorityQueue
    checkQueue=PriorityQueue()
    start=problem.getStartState()
    visited = []
    checkQueue.push((start, []), 0)
    while not checkQueue.isEmpty():
       popState, popDirection= checkQueue.pop()
       if problem.isGoalState(popState):
          return popDirection
       visited.append(popState)
       for successors in problem.getSuccessors(popState):
           state, direction, cost  = successors
           if not state in visited:
              nextState = popDirection+[direction]
              checkQueue.push((state,nextState), problem.getCostOfActions(nextState))

    return[]

def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0

def aStarSearch(problem, heuristic=nullHeuristic):
    """Search the node that has the lowest combined cost and heuristic first."""
    "*** YOUR CODE HERE ***"
    from util import PriorityQueue
    checkQueue=PriorityQueue()
    start=problem.getStartState()
    visited = []
    checkQueue.push((start, []), 0)
    while not checkQueue.isEmpty():
       popState, popDirection= checkQueue.pop()
       if problem.isGoalState(popState):
          return popDirection
       visited.append(popState)
       for successors in problem.getSuccessors(popState):
           state, direction, cost  = successors
           if not state in visited:
              nextState = popDirection+[direction]
              checkQueue.push((state,nextState), problem.getCostOfActions(nextState)+ heuristic(state,problem))

    return[]
    #util.raiseNotDefined()


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
