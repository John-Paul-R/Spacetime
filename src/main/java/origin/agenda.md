# Goals:

## Create world sim
### Components of a world include:
  * Static
    * World Bounds
    * Physics rules

  * Dynamic
    * Bots/bot locations and states
        * (Locations can be represented as probability fields when unknown? (or knn options weighted by closeness))
    * Waves
        * (Probability density field(s) for bullet locations)

World changes are simulated using some form of time evolution operator for each dynamic component (KNN PIF for bots, most likely. Simple robocode physics for bullets/waves)
  * IMPORTANT NOTE: A key feature of this system is that it can take the intermediate changes in OTHER bot's locations into account when running KNN on a given bot, thereby potentially allowing more complex/accurate predictions (as the algorithm essentially has more (admittedly derivitive) information to work off of)

### World State Prediction:
  * From a world state, you should be able to predict the next state
  * a world state should include:
    * Every bot object
        * Each bot's wave list (All Wave States)
    * A time
  * The time is an input for the bot object, used to retrieve the predicted future state(s) of the bot and the waves, thereby essentially creating a new WorldState. The process can now repeat.
    * There will be a "predictNext" method in the WorldState which then iterates through all of the bots and waves and calls their own predictNext methods. Bots (and waves?) should store the most recently predicted values.



## Some sort of "shared manager data" class
(for things like if radar should have control & modes that require something else to be active in another file)

## Implement Global KNN PIF
*Vaguely outlined above. Will expand on this idea later.*

### KNN Notes
#### Possible approaches / things to consider:
* Use some form of clustering (unsupervised classification) in order to essentialy create "bins" in to which knn is applied. (This saves processing on the knn math, but realtime construction of the classification model itself may be too time-complex)

* Use KD Tree nodes as "bins" for knn searches, split nodes after reaching some critical size (Choosing how/on what dimension to split is an interesting problem in itself)
    * Even if it has to access multiple bins, this will still be much more effecient than the O(n) time offered by simply iterating through a list
    * Possible downside: elimination of a large swath of information with early splits/branches. (data point might be relatively far on the first dimension that was split, but extremely close in all others, but it would still be eliminated.) How to account for this issue or remove it entirely?

### KNN Data Structure
The way I think I want to do this is to create a kdtree for all of the State objects, auto segmented and balanced by/on the various dimensions that we are interested in for the KNN search. Additionally, upon creation, every state object will be linked to the previous object, and, later, to the one created immediately after it. In this way, you can always find the "next" or "previous" object, preserving some of the benefits of an ordered list, while still maintaining the search effeciency of a KD Tree.

TODO: add a slight "recency prefrence" to values gotten from kd tree. ATM it seems to prefer the oldest (first acquired) values
Make actual prediction (vs just getting knn states) in the visualizer togglable

### 1v1: what if we track how a bot *changes* how it moves once it is hit (or bullet hit bullet). Similar to displacement vectors w/ knn search, but find take the delta of the delta for a given situation.  How they have changed the way they react to a situation over the course of the game? We can extrapolate from this and potentially predict how they will adapt to being hit.

## Refactor
Create a list/layout of all components of the robot. Make a dedicated function or object for all of them, instead of scattered code all in one file.

RobotBase (getX() overrides, run method, etc.)
  * DataManager (inherited by all manager classes)
  * GunManager
    * 
  * MoveManager
    * 

  * RadarManager
    * 

### Possible new approach to KNN PIF
Run the PIF sequence once (via some WorldManager class). The classes that need to take information from this can do so. (ex GunManager can take each state and perform its own calculations on it)