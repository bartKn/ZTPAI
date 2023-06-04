--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

ALTER ROLE "user" WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:0lfKoBlU1/KYnlPPSDnnsg==$A4QTMVgfHS9ZVi/a1MmVxj8LXVHBl24QtpWdBtcHzps=:L8TvBfE6wZNvw+AI7rovOHtph6zfH40BSk71XlcLf/A=';

--
-- User Configurations
--

--
-- Databases
--

--
-- Database "template1" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2 (Debian 15.2-1.pgdg110+1)
-- Dumped by pg_dump version 15.2 (Debian 15.2-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

UPDATE pg_catalog.pg_database SET datistemplate = false WHERE datname = 'template1';
DROP DATABASE template1;
--
-- Name: template1; Type: DATABASE; Schema: -; Owner: user
--

CREATE DATABASE template1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE template1 OWNER TO "user";

\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: COMMENT; Schema: -; Owner: user
--

COMMENT ON DATABASE template1 IS 'default template for new databases';


--
-- Name: template1; Type: DATABASE PROPERTIES; Schema: -; Owner: user
--

ALTER DATABASE template1 IS_TEMPLATE = true;


\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: ACL; Schema: -; Owner: user
--

REVOKE CONNECT,TEMPORARY ON DATABASE template1 FROM PUBLIC;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;


--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2 (Debian 15.2-1.pgdg110+1)
-- Dumped by pg_dump version 15.2 (Debian 15.2-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE postgres;
--
-- Name: postgres; Type: DATABASE; Schema: -; Owner: user
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE postgres OWNER TO "user";

\connect postgres

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: user
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: friends; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.friends (
    id bigint NOT NULL,
    first_user_id bigint,
    second_user_id bigint
);


ALTER TABLE public.friends OWNER TO "user";

--
-- Name: friends_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.friends_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.friends_id_seq OWNER TO "user";

--
-- Name: friends_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.friends_id_seq OWNED BY public.friends.id;


--
-- Name: refresh_token; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.refresh_token (
    id bigint NOT NULL,
    expiry_date timestamp(6) without time zone,
    token character varying(255),
    user_id bigint
);


ALTER TABLE public.refresh_token OWNER TO "user";

--
-- Name: refresh_token_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.refresh_token_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.refresh_token_id_seq OWNER TO "user";

--
-- Name: refresh_token_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.refresh_token_id_seq OWNED BY public.refresh_token.id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.roles (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.roles OWNER TO "user";

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_id_seq OWNER TO "user";

--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- Name: split; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.split (
    id bigint NOT NULL,
    finished boolean NOT NULL
);


ALTER TABLE public.split OWNER TO "user";

--
-- Name: split_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.split_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.split_id_seq OWNER TO "user";

--
-- Name: split_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.split_id_seq OWNED BY public.split.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    password character varying(255),
    username character varying(255),
    email character varying(255),
    balance numeric(38,2)
);


ALTER TABLE public.users OWNER TO "user";

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO "user";

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: users_roles; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.users_roles (
    user_id bigint NOT NULL,
    roles_id bigint NOT NULL
);


ALTER TABLE public.users_roles OWNER TO "user";

--
-- Name: users_splits_mapping; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.users_splits_mapping (
    split_id bigint NOT NULL,
    contribution numeric(38,2),
    users_contributions_key bigint NOT NULL
);


ALTER TABLE public.users_splits_mapping OWNER TO "user";

--
-- Name: friends id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.friends ALTER COLUMN id SET DEFAULT nextval('public.friends_id_seq'::regclass);


--
-- Name: refresh_token id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.refresh_token ALTER COLUMN id SET DEFAULT nextval('public.refresh_token_id_seq'::regclass);


--
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- Name: split id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.split ALTER COLUMN id SET DEFAULT nextval('public.split_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: friends; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.friends (id, first_user_id, second_user_id) FROM stdin;
48	14	15
49	15	14
50	14	16
51	16	14
\.


--
-- Data for Name: refresh_token; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.refresh_token (id, expiry_date, token, user_id) FROM stdin;
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.roles (id, name) FROM stdin;
1	ROLE_USER
2	ROLE_ADMIN
\.


--
-- Data for Name: split; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.split (id, finished) FROM stdin;
15	f
16	t
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.users (id, password, username, email, balance) FROM stdin;
15	$2a$10$zCVwTtlgWeH8h1cK8oVRmemt50we4fuQTLwsR8Ordl7V1Ul5TQCMS	y	y@email.com	0.00
16	$2a$10$3adB7W9hHcwhTZINLDVX8ukcmn6O0qECyN3gcDwR5yCSfGi9LdXwq	z	z@email.com	0.00
17	$2a$10$XVkx2WgAmrdnjWtBRxGcreCwGgic4HnlvXem.ETy87I7BQQ833pDe	test	test@email.com	0.00
18	$2a$10$xe7NdJCjsX.dd75Cxaw.WO..JTk89y4IduFpmATIqaC7Xfl6tCkB.	test2	test2@email.com	0.00
19	$2a$10$WrukTTm8UXsNBHQkLxY.Ju/JHdAY2.L1sePPIOIMvx46/o69P/tSK	test3	test3@email.com	0.00
14	$2a$10$rpEj9ereZmcfYP2Bn.vomO6ELZzBuj57Q4DOptZdXJGeGnpFs9.cC	x	x@email.com	50.00
\.


--
-- Data for Name: users_roles; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.users_roles (user_id, roles_id) FROM stdin;
14	1
15	1
16	1
17	1
18	1
19	1
\.


--
-- Data for Name: users_splits_mapping; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.users_splits_mapping (split_id, contribution, users_contributions_key) FROM stdin;
15	-1.00	16
15	-1.00	14
15	-1.00	15
16	50.00	15
16	0.00	14
\.


--
-- Name: friends_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.friends_id_seq', 51, true);


--
-- Name: refresh_token_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.refresh_token_id_seq', 195, true);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.roles_id_seq', 2, true);


--
-- Name: split_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.split_id_seq', 16, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.users_id_seq', 19, true);


--
-- Name: friends friends_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.friends
    ADD CONSTRAINT friends_pkey PRIMARY KEY (id);


--
-- Name: refresh_token refresh_token_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT refresh_token_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: split split_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.split
    ADD CONSTRAINT split_pkey PRIMARY KEY (id);


--
-- Name: refresh_token uk_r4k4edos30bx9neoq81mdvwph; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT uk_r4k4edos30bx9neoq81mdvwph UNIQUE (token);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users_splits_mapping users_splits_mapping_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users_splits_mapping
    ADD CONSTRAINT users_splits_mapping_pkey PRIMARY KEY (split_id, users_contributions_key);


--
-- Name: users_roles fk2o0jvgh89lemvvo17cbqvdxaa; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: users_roles fka62j07k5mhgifpp955h37ponj; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT fka62j07k5mhgifpp955h37ponj FOREIGN KEY (roles_id) REFERENCES public.roles(id) ON DELETE CASCADE;


--
-- Name: friends fkaeibdvqqk043o2yiwahir1jor; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.friends
    ADD CONSTRAINT fkaeibdvqqk043o2yiwahir1jor FOREIGN KEY (first_user_id) REFERENCES public.users(id);


--
-- Name: users_splits_mapping fkeaee0bfb9x6hn7lho34djuss0; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users_splits_mapping
    ADD CONSTRAINT fkeaee0bfb9x6hn7lho34djuss0 FOREIGN KEY (users_contributions_key) REFERENCES public.users(id);


--
-- Name: friends fkfhxo04fubf37xdfimy6gkg7ic; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.friends
    ADD CONSTRAINT fkfhxo04fubf37xdfimy6gkg7ic FOREIGN KEY (second_user_id) REFERENCES public.users(id);


--
-- Name: refresh_token fkjtx87i0jvq2svedphegvdwcuy; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT fkjtx87i0jvq2svedphegvdwcuy FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: users_splits_mapping fkkebowenfyktbf9gt1dcp79lse; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users_splits_mapping
    ADD CONSTRAINT fkkebowenfyktbf9gt1dcp79lse FOREIGN KEY (split_id) REFERENCES public.split(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--