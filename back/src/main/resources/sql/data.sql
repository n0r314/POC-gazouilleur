INSERT INTO users(id, username, password, nickname)
VALUES ('1cd7b53d-978f-4c53-8fcd-b1886d5264d2',
        'anonyme',
         /*motdepasse*/
        '{bcrypt}$2y$10$mDFnOJxMocpPdKfrjZif/.nimjDn3OATqtgMOWDiA7sytTNhvyDiO',
        'Auteur anonyme'),

       ('721b199b-08f8-42b3-9363-3fe3ebb387fd',
        'johndoe',
         /*motdepasse*/
        '{bcrypt}$2y$10$mDFnOJxMocpPdKfrjZif/.nimjDn3OATqtgMOWDiA7sytTNhvyDiO',
        'John Doe')

ON CONFLICT DO NOTHING;

INSERT INTO users(id, username, password, nickname, avatar)
VALUES ('3b3c4870-fdf4-4e63-b197-90746f62c3f1',
        'admin',
         /*GoGoGadgetoGo*/
        '{bcrypt}$2y$10$sk60pdx/3zPyWbVgK4ZzjenV1fNqxIB.TgKQNH3CyKUXPdtBb1b9G',
        'Administrateur',
        'https://visualpharm.com/assets/381/Admin-595b40b65ba036ed117d3b23.svg')
ON CONFLICT DO NOTHING ;



/* Gazouillis de base avec l'auteur anonyme par défaut */
INSERT INTO gazouillis(content)
VALUES ('Un premier tweet #first'),
       ('Lorem ipsum')
ON CONFLICT DO NOTHING ;

INSERT INTO gazouillis(id, content)
VALUES ('1620d068-7ca0-4d9f-a2bc-f71675500ea5', 'Un gazouilli avec des commentaires')
ON CONFLICT DO NOTHING ;

INSERT INTO gazouillis(content, author_id)
VALUES ('Un texte écrit par John Doe', '721b199b-08f8-42b3-9363-3fe3ebb387fd')
ON CONFLICT DO NOTHING ;

/* Commentaires sur le gazouilli 'Un gazouilli avec des commentaires'*/
INSERT INTO comments(content, author_id, gazouilli_id)
VALUES ('Un commentaire anonyme', '1cd7b53d-978f-4c53-8fcd-b1886d5264d2', '1620d068-7ca0-4d9f-a2bc-f71675500ea5'),
       ('C''est chouette', '721b199b-08f8-42b3-9363-3fe3ebb387fd', '1620d068-7ca0-4d9f-a2bc-f71675500ea5')
ON CONFLICT DO NOTHING ;
