import React, { Component } from 'react';
import { SERVER_URL } from '../constants.js';
import ReactTable from "react-table";
import 'react-table/react-table.css';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import AddCar from './AddCar.js';
import { thisTypeAnnotation } from '@babel/types';
import { CSVLink } from 'react-csv';


class Carlist extends Component {
    
    constructor(props) {
        super(props);
        this.state = { cars: [] };
    }

    componentDidMount() {
        this.fetchCars();
    }

    fetchCars = () => {
        fetch(SERVER_URL + 'api/cars')
        .then((response) => response.json())
        .then((responseData) => {
            this.setState({
                cars: responseData._embedded.cars,
            });
        })
        .catch(err => console.error(err));
    }

    onDelClick = (link) => {
        fetch(link, {method: 'DELETE'})
        .then(res => {
            toast.success("Car deleted", {
                position: toast.POSITION.BOTTOM_LEFT
            });
            this.fetchCars();
        })
        .catch(err => {
            toast.success("Error while deleting", {
                position: toast.POSITION.BOTTOM_LEFT
            });
            console.error(err);
        })
    }

    confirmDelete = (link) => {
        confirmAlert({
            message: 'Are you sure you want to delete?',
            buttons: [
                {
                    label: 'Yes',
                    onClick: () => this.onDelClick(link)
                },
                {
                    label: 'No'
                }
            ]
        })
    }

    addCar(car) {
        fetch(SERVER_URL + 'api/cars',
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(car)
        })
        .then(res => this.fetchCars())
        .catch(err => console.error(err))
    }

    renderEditable = (cellInfo) => {
        return (
            <div style={{ backgroundColor: "#fafafa" }} contentEditable suppressContentEditableWarning onBlur={e => {
                const data = [...this.state.cars];
                data[cellInfo.index][cellInfo.column.id] = e.target.innerHTML;
                this.setState({ cars: data });
            }} dangerouslySetInnerHTML={{
                __html: this.state.cars[cellInfo.index][cellInfo.column.id]
            }} />
        )
    }

    updateCar(car, link) {
        fetch(link, 
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(car)
            })
            .then(res => toast.success("Changes Saved", {
                position: toast.POSITION.BOTTOM_LEFT
            })
        )
        .catch(err => toast.error("Error when saving", {
            position: toast.POSITION.BOTTOM_LEFT
            })
        )
    }

    render() {
        const columns = [{
            Header: 'Brand',
            accessor: 'brand',
            Cell: this.renderEditable
        }, {
            Header: 'Model',
            accessor: 'model',
            Cell: this.renderEditable
        }, {
            Header: 'Colour',
            accessor: 'color',
            Cell: this.renderEditable
        }, {
            Header: 'Year',
            accessor: 'year',
            Cell: this.renderEditable
        }, {
            Header: 'Price £',
            accessor: 'price',
            Cell: this.renderEditable
        }, {
            id: 'savebutton',
            sortable: false,
            filterable: false,
            width: 100,
            accessor: '_links.self.href',
            Cell: ({value, row}) => (<button onClick={() => {this.updateCar(row, value)}}>Save</button>)
        }, {
            id: 'delbutton',
            sortable: false,
            filterable: false,
            width: 100,
            accessor: '_links.self.href',
            Cell: ({value}) => (<button onClick={()=>{this.confirmDelete(value)}}>Delete</button>)
        }]
        return (
            <div className="App">
                <CSVLink data={this.state.cars} separator=";">Export CSV</CSVLink>
                <AddCar addCar={this.addCar} fetchCars={this.fetchCars} />
                <ReactTable data={this.state.cars} columns={columns} filterable={true} />
                <ToastContainer autoClose={1500} />
            </div>
        );
    }
}

export default Carlist;