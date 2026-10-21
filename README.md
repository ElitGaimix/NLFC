# NLFC : Flight Controller

NLFC est une application de station de contrôle au sol. Elle est conçue pour s'exécuter sur un ordinateur fixe ou portable équipé du JDK 25 et relié à un module radio sous [RocketLRS](https://github.com/ElitGaimix/RocketLRS).

## Objectif

L'objectif de NLFC est de maintenir une communication fiable avec la fusée durant toutes les phases de vol et d'enregistrer un maximum d'informations.

Actuellement, NLFC assure la communication avec la fusée et vérifie l'intégrité des paquets reçus à l'aide d'un CRC. L'adaptation de la liaison RocketLRS aux phases de vol et l'enregistrement des données font partie des objectifs à développer.

La priorité est le suivi du vol et la collecte des informations. Le contrôle de la fusée n'est pas prévu pour l'instant, à l'exception d'une éventuelle commande manuelle d'éjection du parachute par l'utilisateur.

## Plus tard

Des mises à jour à distance pourraient être envisagées, sous réserve des limites de débit de la liaison. Un système de contrôle pourrait également être développé pour les prochaines versions de fusée.

## Contributions

Les contributions ne sont pas ouvertes pour le moment.
