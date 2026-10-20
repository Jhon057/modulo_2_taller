select h.id,
       h.nombre,
       h.apellido,
       r.fecha_ingreso,
       r.fecha_salida,
       h3.nombre,
       h2.numero,
       r.fecha_salida - r.fecha_ingreso as dias
from reservas r
inner join public.huespedes h on h.id = r.huesped_id
inner join public.habitaciones h2 on h2.id = r.habitacion_id
inner join public.hoteles h3 on h3.id = h2.hotel_id
order by h.id;


select *
from reservas r
inner join public.huespedes h on h.id = r.huesped_id
inner join public.habitaciones h2 on h2.id = r.habitacion_id
inner join public.reserva_servicio rs on r.id = rs.reserva_id
inner join public.servicios s on s.id = rs.servicio_id;


SELECT *
FROM reservas;