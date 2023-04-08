import React, { useState } from 'react';
import {
    MDBNavbar,
    MDBContainer,
    MDBIcon,
    MDBNavbarNav,
    MDBNavbarItem,
    MDBNavbarLink,
    MDBNavbarToggler,
    MDBNavbarBrand,
    MDBCollapse
} from 'mdb-react-ui-kit';

const NavBar = () => {
    const [showNavColor, setShowNavColor] = useState(false);

    return (
        <>
            <MDBNavbar expand='lg' dark bgColor='primary'>
                <MDBContainer fluid>
                    <MDBNavbarBrand href='#'>Navbar</MDBNavbarBrand>
                    <MDBNavbarToggler
                        type='button'
                        data-target='#navbarColor02'
                        aria-controls='navbarColor02'
                        aria-expanded='false'
                        aria-label='Toggle navigation'
                        onClick={() => setShowNavColor(!showNavColor)}
                    >
                        <MDBIcon icon='bars' fas />
                    </MDBNavbarToggler>
                    <MDBCollapse show={showNavColor} navbar>
                        <MDBNavbarNav className='me-auto mb-2 mb-lg-0'>
                            <MDBNavbarItem className='active'>
                                <MDBNavbarLink aria-current='page' href='#'>
                                    Home
                                </MDBNavbarLink>
                            </MDBNavbarItem>
                            <MDBNavbarItem>
                                <MDBNavbarLink href='/profile'>
                                    <MDBIcon icon="user" size="2x" />
                                </MDBNavbarLink>
                            </MDBNavbarItem>
                            <MDBNavbarItem>
                                <MDBNavbarLink href='#'>
                                    <MDBIcon icon="users" size="2x" />
                                </MDBNavbarLink>
                            </MDBNavbarItem>

                        </MDBNavbarNav>
                        <MDBNavbarNav right>
                            <MDBNavbarItem>
                                <MDBNavbarLink href='#'>
                                    <MDBIcon icon="sign-out-alt" size="2x" />
                                </MDBNavbarLink>
                            </MDBNavbarItem>
                        </MDBNavbarNav>
                    </MDBCollapse>
                </MDBContainer>
            </MDBNavbar>
        </>
    );
}

export default NavBar;