create extension if not exists pg_trgm;

create or replace function f_orphans(_table anyelement)
    returns setof anyelement as
$func$
begin
    return query execute (
        select
            format(e'select * from %s t\nwhere not exists (select from %s',
                c.confrelid::regclass,
                string_agg(format('%s where %s = %s)', c.conrelid::regclass, source.columns, target.columns), e'\nand not exists (select from '))
                from pg_constraint c, cardinality(c.conkey) as column_count,
            lateral (
                select concat(case when column_count > 1 then '(' end,
                string_agg(quote_ident(attname), ', ' order by fld.ord),
                case when column_count > 1 then ')' end) as columns
                from unnest(c.conkey) with ordinality fld(attnum, ord)
                join pg_catalog.pg_attribute a on (a.attrelid, a.attnum) = (c.conrelid, fld.attnum)
            ) source,
            lateral (
                select concat(case when column_count > 1 then '(' end,
                string_agg('t.' || quote_ident(attname), ', t.' order by fld.ord),
                case when column_count > 1 then ')' end) as columns
                from unnest(c.confkey) with ordinality fld(attnum, ord)
                join pg_catalog.pg_attribute a on (a.attrelid, a.attnum) = (c.confrelid, fld.attnum)
            ) target
        where c.confrelid = pg_typeof(_table)::text::regclass
        and c.contype = 'f'
        group by c.confrelid
    );
end
$func$ language plpgsql;