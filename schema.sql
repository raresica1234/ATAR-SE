--
-- PostgreSQL database dump
--

-- Dumped from database version 12.7 (Ubuntu 12.7-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.7 (Ubuntu 12.7-0ubuntu0.20.04.1)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: bids; Type: TABLE; Schema: public; Owner: atarse
--

CREATE TABLE public.bids (
    proposalid integer NOT NULL,
    pcmemberid integer,
    bidtype character varying(50),
    approved boolean
);


ALTER TABLE public.bids OWNER TO atarse;

--
-- Name: bids_id_seq; Type: SEQUENCE; Schema: public; Owner: atarse
--

CREATE SEQUENCE public.bids_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.bids_id_seq OWNER TO atarse;

--
-- Name: bids_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: atarse
--

ALTER SEQUENCE public.bids_id_seq OWNED BY public.bids.proposalid;


--
-- Name: chats; Type: TABLE; Schema: public; Owner: atarse
--

CREATE TABLE public.chats (
    id integer NOT NULL,
    userid integer NOT NULL,
    proposalid integer NOT NULL,
    message character varying(256) NOT NULL,
    "timestamp" timestamp without time zone
);


ALTER TABLE public.chats OWNER TO atarse;

--
-- Name: chats_id_seq; Type: SEQUENCE; Schema: public; Owner: atarse
--

CREATE SEQUENCE public.chats_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.chats_id_seq OWNER TO atarse;

--
-- Name: chats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: atarse
--

ALTER SEQUENCE public.chats_id_seq OWNED BY public.chats.id;


--
-- Name: conferences; Type: TABLE; Schema: public; Owner: atarse
--

CREATE TABLE public.conferences (
    id integer NOT NULL,
    name character varying(50),
    abstractdeadline date,
    paperdeadline date,
    biddingdeadline date,
    reviewdeadline date,
    reviewercount integer
);


ALTER TABLE public.conferences OWNER TO atarse;

--
-- Name: conferences_id_seq; Type: SEQUENCE; Schema: public; Owner: atarse
--

CREATE SEQUENCE public.conferences_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.conferences_id_seq OWNER TO atarse;

--
-- Name: conferences_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: atarse
--

ALTER SEQUENCE public.conferences_id_seq OWNED BY public.conferences.id;


--
-- Name: participants; Type: TABLE; Schema: public; Owner: atarse
--

CREATE TABLE public.participants (
    userid integer NOT NULL,
    sectionid integer NOT NULL,
    isspeaker boolean
);


ALTER TABLE public.participants OWNER TO atarse;

--
-- Name: proposalauthors; Type: TABLE; Schema: public; Owner: atarse
--

CREATE TABLE public.proposalauthors (
    authorid integer NOT NULL,
    proposalid integer NOT NULL,
    notification boolean DEFAULT false NOT NULL
);


ALTER TABLE public.proposalauthors OWNER TO atarse;

--
-- Name: proposals; Type: TABLE; Schema: public; Owner: atarse
--

CREATE TABLE public.proposals (
    id integer NOT NULL,
    abstractpaper character varying(1500),
    fullpaper character varying(1024),
    name character varying(50),
    keywords character varying(100),
    topics character varying(150),
    conferenceid integer,
    sectionid integer,
    status character varying(50),
    presentation character varying(1024) DEFAULT ''::character varying
);


ALTER TABLE public.proposals OWNER TO atarse;

--
-- Name: proposals_id_seq; Type: SEQUENCE; Schema: public; Owner: atarse
--

CREATE SEQUENCE public.proposals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.proposals_id_seq OWNER TO atarse;

--
-- Name: proposals_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: atarse
--

ALTER SEQUENCE public.proposals_id_seq OWNED BY public.proposals.id;


--
-- Name: reviews; Type: TABLE; Schema: public; Owner: atarse
--

CREATE TABLE public.reviews (
    proposalid integer NOT NULL,
    pcmemberid integer NOT NULL,
    reviewtype character varying(50),
    recommendation character varying(500)
);


ALTER TABLE public.reviews OWNER TO atarse;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: atarse
--

CREATE TABLE public.roles (
    userid integer NOT NULL,
    conferenceid integer NOT NULL,
    roletype character varying(50)
);


ALTER TABLE public.roles OWNER TO atarse;

--
-- Name: rooms; Type: TABLE; Schema: public; Owner: atarse
--

CREATE TABLE public.rooms (
    id integer NOT NULL,
    seatcount integer
);


ALTER TABLE public.rooms OWNER TO atarse;

--
-- Name: rooms_id_seq; Type: SEQUENCE; Schema: public; Owner: atarse
--

CREATE SEQUENCE public.rooms_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rooms_id_seq OWNER TO atarse;

--
-- Name: rooms_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: atarse
--

ALTER SEQUENCE public.rooms_id_seq OWNED BY public.rooms.id;


--
-- Name: sections; Type: TABLE; Schema: public; Owner: atarse
--

CREATE TABLE public.sections (
    id integer NOT NULL,
    conferenceid integer,
    sessionchairid integer,
    startdate date,
    roomid integer,
    name character varying(50),
    enddate date
);


ALTER TABLE public.sections OWNER TO atarse;

--
-- Name: sections_id_seq; Type: SEQUENCE; Schema: public; Owner: atarse
--

CREATE SEQUENCE public.sections_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sections_id_seq OWNER TO atarse;

--
-- Name: sections_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: atarse
--

ALTER SEQUENCE public.sections_id_seq OWNED BY public.sections.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: atarse
--

CREATE TABLE public.users (
    id integer NOT NULL,
    email character varying(50),
    password character varying(50),
    firstname character varying(50),
    affiliation character varying(50),
    webpagelink character varying(50),
    issiteadministrator boolean,
    lastname character varying(50)
);


ALTER TABLE public.users OWNER TO atarse;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: atarse
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO atarse;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: atarse
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: bids proposalid; Type: DEFAULT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.bids ALTER COLUMN proposalid SET DEFAULT nextval('public.bids_id_seq'::regclass);


--
-- Name: chats id; Type: DEFAULT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.chats ALTER COLUMN id SET DEFAULT nextval('public.chats_id_seq'::regclass);


--
-- Name: conferences id; Type: DEFAULT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.conferences ALTER COLUMN id SET DEFAULT nextval('public.conferences_id_seq'::regclass);


--
-- Name: proposals id; Type: DEFAULT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.proposals ALTER COLUMN id SET DEFAULT nextval('public.proposals_id_seq'::regclass);


--
-- Name: rooms id; Type: DEFAULT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.rooms ALTER COLUMN id SET DEFAULT nextval('public.rooms_id_seq'::regclass);


--
-- Name: sections id; Type: DEFAULT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.sections ALTER COLUMN id SET DEFAULT nextval('public.sections_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: chats chats_pk; Type: CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT chats_pk PRIMARY KEY (id);


--
-- Name: conferences conferences_pkey; Type: CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.conferences
    ADD CONSTRAINT conferences_pkey PRIMARY KEY (id);


--
-- Name: reviews pk_proposal_member; Type: CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT pk_proposal_member PRIMARY KEY (proposalid, pcmemberid);


--
-- Name: roles pk_userid_conferenceid; Type: CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT pk_userid_conferenceid PRIMARY KEY (userid, conferenceid);


--
-- Name: participants pk_userid_sectionid; Type: CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.participants
    ADD CONSTRAINT pk_userid_sectionid PRIMARY KEY (userid, sectionid);


--
-- Name: proposalauthors proposalauthors_pkey; Type: CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.proposalauthors
    ADD CONSTRAINT proposalauthors_pkey PRIMARY KEY (authorid, proposalid);


--
-- Name: proposals proposals_pkey; Type: CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.proposals
    ADD CONSTRAINT proposals_pkey PRIMARY KEY (id);


--
-- Name: rooms rooms_pkey; Type: CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT rooms_pkey PRIMARY KEY (id);


--
-- Name: sections section_pkey; Type: CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.sections
    ADD CONSTRAINT section_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: bids bids_pc_memberid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.bids
    ADD CONSTRAINT bids_pc_memberid_fkey FOREIGN KEY (pcmemberid) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: bids bids_proposalid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.bids
    ADD CONSTRAINT bids_proposalid_fkey FOREIGN KEY (proposalid) REFERENCES public.proposals(id) ON DELETE CASCADE;


--
-- Name: chats chats_proposal_fk; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT chats_proposal_fk FOREIGN KEY (proposalid) REFERENCES public.proposals(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: chats chats_user_fk; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT chats_user_fk FOREIGN KEY (userid) REFERENCES public.users(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: participants participants_sectionid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.participants
    ADD CONSTRAINT participants_sectionid_fkey FOREIGN KEY (sectionid) REFERENCES public.sections(id) ON DELETE CASCADE;


--
-- Name: participants participants_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.participants
    ADD CONSTRAINT participants_userid_fkey FOREIGN KEY (userid) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: proposalauthors proposalauthors_authorid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.proposalauthors
    ADD CONSTRAINT proposalauthors_authorid_fkey FOREIGN KEY (authorid) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: proposalauthors proposalauthors_proposalid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.proposalauthors
    ADD CONSTRAINT proposalauthors_proposalid_fkey FOREIGN KEY (proposalid) REFERENCES public.proposals(id) ON DELETE CASCADE;


--
-- Name: proposals proposals_conferenceid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.proposals
    ADD CONSTRAINT proposals_conferenceid_fkey FOREIGN KEY (conferenceid) REFERENCES public.conferences(id) ON DELETE CASCADE;


--
-- Name: reviews reviews_pcmemberid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_pcmemberid_fkey FOREIGN KEY (pcmemberid) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: reviews reviews_proposalid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_proposalid_fkey FOREIGN KEY (proposalid) REFERENCES public.proposals(id) ON DELETE CASCADE;


--
-- Name: roles roles_conferenceid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_conferenceid_fkey FOREIGN KEY (conferenceid) REFERENCES public.conferences(id) ON DELETE CASCADE;


--
-- Name: roles roles_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_userid_fkey FOREIGN KEY (userid) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: sections section_conferenceid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.sections
    ADD CONSTRAINT section_conferenceid_fkey FOREIGN KEY (conferenceid) REFERENCES public.conferences(id) ON DELETE CASCADE;


--
-- Name: sections section_sessionchairid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: atarse
--

ALTER TABLE ONLY public.sections
    ADD CONSTRAINT section_sessionchairid_fkey FOREIGN KEY (sessionchairid) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

