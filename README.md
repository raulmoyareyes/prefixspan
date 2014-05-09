PrefixSpan
==========


 PrefiSpan --- An Implementation of Prefix-projected Sequential Pattern mining on Java

 Authors: Raúl Moya Reyes <raulmoya.es>
          Agustín Ruiz Linares <agustruiz.es>
          University of Jaén

          (Based on Yasuo Tabei <tabei@cb.k.u-tokyo.ac.jp> code in C++, University of Tokyo)


==========================================================================================

FORMAT OF params.txt FILE

  That file must contain 3 rows:
    PATH_TO_DATA_FILE
    MINIMUM_SUPPORT
    MAXIMUM:PATTERN

==========================================================================================

FORMAT OF INPUT DATA:

  3 1 3 4 5
  2 3 1
  3 4 4 3
  1 3 4 5
  2 4 1 
  6 5 3 
 
  Each line corresponds to the each transaction which has a sequence of items separated by single space.

==========================================================================================

FORMAT OF RESULTS:

  itemsets
  ( ids ) freq
  itemsets
  ( ids ) freq
  itemsets
  ( ids ) freq
  ... 

  This result means:

  FREQUENT SEQUENCE   : TRANSACTION ID : FREQUENCY
  1                       0 1 3 4          4
  1 3                     0 3              2
  1 3 4                   0 3              2
  1 3 4 5                 0 3              2
  1 3 5                   0 3              2
  1 4                     0 3              2
  ...

  Each line represents the frequent sequences
  whose frequency is no less than min_sup (-min_sup option) and the size of sequences
  is less than or equal max_pat (-max_pat option).
