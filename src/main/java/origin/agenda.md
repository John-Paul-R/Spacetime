# Goals:

## Create world sim
Components of a world include:
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
