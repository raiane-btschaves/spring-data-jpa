select b1_0.id,
       b1_0.id_author,
       aut.id,
       a1_0.date_birth,
       a1_0.name,
       a1_0.nationality,
       b1_0.gender,
       b1_0.isbn,
       b1_0.price,
       b1_0.publication_date,
       b1_0.title
from book b1_0
         join public.author aut on aut.id = b1_0.id_author
where b1_0.id = ?